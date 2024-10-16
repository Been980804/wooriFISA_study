## Terraform을 활용해 AWS bucket 만들기

### 목표
```
  1. 정책 권한 승인
  2. 버킷만 생성
  3. 새로운 index.html 업로드
  4. 이미 존재하는 index.html 로컬에서 수정 후 업로드
  5. 새로운 main.html 업로드
  6. 리드미 작업
```

<br>

### 1. 정책 권한 승인
- provider.tf
    - 정책, 역할 생성

```bash
# IAM 역할 생성
resource "aws_iam_role" "ce29-s3-role" {
  name = "ce29-s3-role"
  
  assume_role_policy = jsonencode({
    "Version": "2012-10-17",
    "Statement": [
      {
        "Action": "sts:AssumeRole",
        "Effect": "Allow",
        "Principal": {
          "Service": "ec2.amazonaws.com"
        }
      }
    ]
  })
}

# IAM 정책 정의 (S3에 대한 모든 권한 부여)
resource "aws_iam_policy" "ce29-s3-access-policy" {
  name        = "ce29-s3-access-policy"
  description = "Full access to S3 resources"
  
  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"
        Action = [
          "s3:*"  # 모든 S3 액세스 허용
        ]
        Resource = [
          "*"  # 모든 S3 리소스에 대한 권한
        ]
      }
    ]
  })
}

# IAM 역할에 정책 연결
resource "aws_iam_role_policy_attachment" "attach_s3_policy" {
  role       = aws_iam_role.ce29-s3-role.name
  policy_arn = aws_iam_policy.ce29-s3-access-policy.arn
}
```

<br>

### 2. 버킷만 생성
- bucket.tf    
```bash
# S3 버킷 생성 + index.html 업로드
    
# S3 버킷 생성
resource "aws_s3_bucket" "bucket2" {
  bucket = "ce29-bucket2"  # 생성하고자 하는 S3 버킷 이름
}
    
# S3 버킷의 public access block 설정
resource "aws_s3_bucket_public_access_block" "bucket2_public_access_block" {
  bucket = aws_s3_bucket.bucket2.id
    
  block_public_acls       = false
  block_public_policy     = false
  ignore_public_acls      = false
  restrict_public_buckets = false
}
    
# S3 버킷의 웹사이트 호스팅 설정
resource "aws_s3_bucket_website_configuration" "xweb_bucket_website" {
  bucket = aws_s3_bucket.bucket2.id  # 생성된 S3 버킷 이름 사용
    
  index_document {
    suffix = "index.html"
  }
}
    
# S3 버킷의 public read 정책 설정
resource "aws_s3_bucket_policy" "public_read_access" {
  bucket = aws_s3_bucket.bucket2.id  # 생성된 S3 버킷 이름 사용
    
  policy = <<EOF
    {
      "Version": "2012-10-17",
      "Statement": [
        {
          "Effect": "Allow",
          "Principal": "*",
          "Action": [ "s3:GetObject" ],
          "Resource": [
            "arn:aws:s3:::ce29-bucket2",
            "arn:aws:s3:::ce29-bucket2/*"
          ]
        }
      ]
    }
    EOF
    }
```

<br>

### 3. upload_index.tf

```bash
# 이미 존재하는 S3 버킷에 index.html 파일을 업로드
resource "aws_s3_object" "index" {
  bucket        = aws_s3_bucket.bucket2.id  # 생성된 S3 버킷 이름 사용
  key           = "index.html"
  source        = "index.html"
  content_type  = "text/html"
}
```

<br>

### 4. reupload_index.tf
```bash
resource "aws_s3_object" "index" {
  bucket        = aws_s3_bucket.bucket2.id  # 생성된 S3 버킷 이름 사용
  key           = "index.html"
  source        = "index.html"
  content_type  = "text/html"
  etag          = filemd5("index.html")
}
```

<br>

### 5. upload_jongwon.tf
```bash
resource "aws_s3_object" "jongwon" {
  bucket        = aws_s3_bucket.bucket2.id  # 생성된 S3 버킷 이름 사용
  key           = "jongwon.html"
  source        = "jongwon.html"
  content_type  = "text/html"
  etag          = filemd5("jongwon.html")
}

# image.jpg 파일을 업로드
resource "aws_s3_object" "jongwon1" {
  bucket        = aws_s3_bucket.bucket2.id
  key           = "imgs/s3_test.jpg"    # S3 내 경로 설정 (예: images 폴더)
  source        = "s3_test.jpg"  # 로컬 파일 경로
  content_type  = "image/jpeg"          # 이미지 파일의 콘텐츠 타입
}

# image.jpg 파일을 업로드
resource "aws_s3_object" "jongwon2" {
  bucket        = aws_s3_bucket.bucket2.id
  key           = "imgs/s3_test2.jpg"    # S3 내 경로 설정 (예: images 폴더)
  source        = "s3_test2.jpg"  # 로컬 파일 경로
  content_type  = "image/jpeg"          # 이미지 파일의 콘텐츠 타입
}
```

<br>

### 6. endpoint 설정
- output.tf

```bash
output "website_endpoint" {
  value = aws_s3_bucket.bucket2.website_endpoint
  description = "The endpoint for the S3 bucket website."
}
```

<br>

### 결과

![image](https://github.com/user-attachments/assets/ccac9265-6d60-4ae7-b31e-b9a97a845159)

### web site
<p align="center">  
  <img src="https://github.com/user-attachments/assets/9244f00f-9941-4ac6-a82b-1f19ae8d10e8" align="top" width="45%">
  <img src="https://github.com/user-attachments/assets/61b60051-90dc-4d66-bf16-6b2d16344431" align="center" width="45%">
</p>

## html 수정후 반영

```bash
terraform init
terraform plan
terraform apply -auto-approve
```

### 

![image](https://github.com/user-attachments/assets/dce20b2e-a9f4-46ba-af15-3e75e9a1bde4)

### web site

<p align="center">  
  <img src="https://github.com/user-attachments/assets/8f4bd28c-8d8d-4225-86d5-b803f20ef153" align="top" width="45%">
  <img src="https://github.com/user-attachments/assets/752cd2a6-b0e4-412d-bd53-97fcf5a4b0bf" align="center" width="45%">
</p>
