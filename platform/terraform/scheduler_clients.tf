# scheduler clients vars
variable "scheduler_client_role" {
  default = "scheduler-client"
}
variable "scheduler_producer_role" {
  default = "scheduler-producer"
}

# scheduler client droplets and ansible inventory

# producers
resource "digitalocean_droplet" "scheduler_producer_01_droplet" {
  image = "${var.droplet_image}"
  name = "${var.scheduler_producer_role}-01"
  region = "${var.primary_datacenter_name}"
  size = "${var.droplet_size}"
  private_networking = true
  ssh_keys = [
    "${var.ssh_fingerprint}"]
  tags = [
    "${var.target_env}",
    "${var.scheduler_client_role}",
    "${var.scheduler_producer_role}",
    "${var.discovery_client_role}",
    "${var.java_runtime_role}"]
}

resource "ansible_host" "scheduler_producer_01_droplet" {
  inventory_hostname = "${digitalocean_droplet.scheduler_producer_01_droplet.name}"
  groups = [
    "${var.target_env}",
    "${var.discovery_client_role}",
    "${var.scheduler_client_role}",
    "${var.scheduler_producer_role}",
    "${var.java_runtime_role}"]
  vars {
    ansible_host = "${digitalocean_droplet.scheduler_producer_01_droplet.ipv4_address}"
    ansible_python_interpreter = "${var.ansible_python_interpreter}"
    datacenter_name = "${var.primary_datacenter_name}"
    datacenter_role = "${var.primary_datacenter_role}"
  }
}

