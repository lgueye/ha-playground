# cache cluster vars
variable "cache_server_role" {
  default = "cache-server"
}

resource "digitalocean_droplet" "cache_server_01_droplet" {
  image = "${var.droplet_image}"
  name = "${var.cache_server_role}-01"
  region = "${var.primary_datacenter_name}"
  size = "${var.droplet_size}"
  private_networking = true
  ssh_keys = [
    "${var.ssh_fingerprint}"]
  tags = [
    "${var.target_env}",
    "${var.discovery_client_role}",
    "${var.cache_server_role}",
    "${var.java_runtime_role}"]
}

resource "ansible_host" "cache_server_01_droplet" {
  inventory_hostname = "${digitalocean_droplet.cache_server_01_droplet.name}"
  groups = [
    "${var.target_env}",
    "${var.discovery_client_role}",
    "${var.cache_server_role}",
    "${var.primary_datacenter_role}",
    "${var.java_runtime_role}"]
  vars {
    ansible_host = "${digitalocean_droplet.cache_server_01_droplet.ipv4_address}"
    ansible_python_interpreter = "${var.ansible_python_interpreter}"
    datacenter_name = "${var.primary_datacenter_name}"
    datacenter_role = "${var.primary_datacenter_role}"
  }
}
resource "digitalocean_droplet" "cache_server_02_droplet" {
  image = "${var.droplet_image}"
  name = "${var.cache_server_role}-02"
  region = "${var.fallback_datacenter_name}"
  size = "${var.droplet_size}"
  private_networking = true
  ssh_keys = [
    "${var.ssh_fingerprint}"]
  tags = [
    "${var.target_env}",
    "${var.discovery_client_role}",
    "${var.cache_server_role}",
    "${var.java_runtime_role}"]
}

resource "ansible_host" "cache_server_02_droplet" {
  inventory_hostname = "${digitalocean_droplet.cache_server_02_droplet.name}"
  groups = [
    "${var.target_env}",
    "${var.discovery_client_role}",
    "${var.cache_server_role}",
    "${var.fallback_datacenter_role}",
    "${var.java_runtime_role}"]
  vars {
    ansible_host = "${digitalocean_droplet.cache_server_02_droplet.ipv4_address}"
    ansible_python_interpreter = "${var.ansible_python_interpreter}"
    datacenter_name = "${var.fallback_datacenter_name}"
    datacenter_role = "${var.fallback_datacenter_role}"
  }
}

resource "digitalocean_droplet" "cache_server_03_droplet" {
  image = "${var.droplet_image}"
  name = "${var.cache_server_role}-03"
  region = "${var.ternary_datacenter_name}"
  size = "${var.droplet_size}"
  private_networking = true
  ssh_keys = [
    "${var.ssh_fingerprint}"]
  tags = [
    "${var.target_env}",
    "${var.discovery_client_role}",
    "${var.cache_server_role}",
    "${var.java_runtime_role}"]
}

resource "ansible_host" "cache_server_03_droplet" {
  inventory_hostname = "${digitalocean_droplet.cache_server_03_droplet.name}"
  groups = [
    "${var.target_env}",
    "${var.discovery_client_role}",
    "${var.cache_server_role}",
    "${var.ternary_datacenter_role}",
    "${var.java_runtime_role}"]
  vars {
    ansible_host = "${digitalocean_droplet.cache_server_03_droplet.ipv4_address}"
    ansible_python_interpreter = "${var.ansible_python_interpreter}"
    datacenter_role = "${var.ternary_datacenter_role}"
    datacenter_name = "${var.ternary_datacenter_name}"
  }
}
