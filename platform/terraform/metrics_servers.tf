# metrics cluster vars
variable "metrics_server_role" {
  default = "metrics-server"
}
variable "alerts_server_role" {
  default = "alerts-server"
}

resource "digitalocean_droplet" "metrics_server_01_droplet" {
  image = "${var.droplet_image}"
  name = "${var.metrics_server_role}-01"
  region = "${var.primary_datacenter_name}"
  size = "${var.droplet_size}"
  private_networking = true
  ssh_keys = [
    "${var.ssh_fingerprint}"]
  tags = [
    "${var.target_env}",
    "${var.metrics_server_role}",
    "${var.alerts_server_role}"]
}

resource "ansible_host" "metrics_server_01_droplet" {
  inventory_hostname = "${digitalocean_droplet.metrics_server_01_droplet.name}"
  groups = [
    "${var.target_env}",
    "${var.metrics_server_role}",
    "${var.alerts_server_role}",
    "${var.primary_datacenter_role}"]
  vars {
    ansible_host = "${digitalocean_droplet.metrics_server_01_droplet.ipv4_address}"
    ansible_python_interpreter = "${var.ansible_python_interpreter}"
    datacenter_name = "${var.primary_datacenter_name}"
    datacenter_role = "${var.primary_datacenter_role}"
  }
}
