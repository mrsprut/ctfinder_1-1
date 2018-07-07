sudo ./google-cloud-sdk/bin/gcloud auth login

sudo ./google-cloud-sdk/bin/gcloud config set project creativetfinder

sudo ./google-cloud-sdk/bin/gcloud datastore create-indexes /mnt/C/eclipse-workspace/ctfinder_1-1/src/main/webapp/WEB-INF/index.yaml

sudo ./google-cloud-sdk/bin/gcloud datastore cleanup-indexes /mnt/C/eclipse-workspace/ctfinder_1-1/src/main/webapp/WEB-INF/index.yaml