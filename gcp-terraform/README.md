## GCP Terraform
### 설정 해줘야하는 것들
credential 설정
- `gcloud auth login`
- `gcloud auth application-default login`
- `export GOOGLE_APPLICATION_CREDENTIALS="path/to/credential.json"`
API 설정
- Cloud SQL Admin API 활성화
- Compute Engine API 활성화
- Cloud Run Admin API 활성화
- Artifact Registry API 활성화