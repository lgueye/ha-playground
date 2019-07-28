Plan
---

```
echo yes | terraform plan -var "do_token=${DO_API_TOKEN}" -var "pub_key=${HOME}/.ssh/id_rsa.pub" -var "pvt_key=${HOME}/.ssh/id_rsa" -var "ssh_fingerprint=`ssh-keygen -lf ~/.ssh/id_rsa.pub -E md5  | awk '{ print $2 }' | cut -c 5-`" -var "target_env=staging"
```

Apply
---

```
echo yes | terraform apply -var "do_token=${DO_API_TOKEN}" -var "pub_key=${HOME}/.ssh/id_rsa.pub" -var "pvt_key=${HOME}/.ssh/id_rsa" -var "ssh_fingerprint=`ssh-keygen -lf ~/.ssh/id_rsa.pub -E md5  | awk '{ print $2 }' | cut -c 5-`" -var "target_env=staging"
```

Destroy
---

```
echo yes | terraform destroy -var "do_token=${DO_API_TOKEN}" -var "pub_key=${HOME}/.ssh/id_rsa.pub" -var "pvt_key=${HOME}/.ssh/id_rsa" -var "ssh_fingerprint=`ssh-keygen -lf ~/.ssh/id_rsa.pub -E md5  | awk '{ print $2 }' | cut -c 5-`" -var "target_env=staging"
```
