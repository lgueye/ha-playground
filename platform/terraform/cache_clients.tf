# cache clients vars
variable "cache_client_role" {
  default = "cache-client"
}
variable "cache_consumer_role" {
  default = "cache-consumer"
}
variable "cache_producer_role" {
  default = "cache-producer"
}

# cache client droplets and ansible inventory

# consumer
resource "digitalocean_droplet" "cache_consumer_01_droplet" {
  image = "${var.droplet_image}"
  name = "${var.cache_consumer_role}-01"
  region = "${var.primary_datacenter_name}"
  size = "${var.droplet_size}"
  private_networking = true
  ssh_keys = [
    "${var.ssh_fingerprint}"]
  tags = [
    "${var.target_env}",
    "${var.cache_client_role}",
    "${var.cache_consumer_role}",
    "${var.discovery_client_role}",
    "${var.java_runtime_role}"]
}

resource "ansible_host" "cache_consumer_01_droplet" {
  inventory_hostname = "${digitalocean_droplet.cache_consumer_01_droplet.name}"
  groups = [
    "${var.target_env}",
    "${var.discovery_client_role}",
    "${var.cache_client_role}",
    "${var.cache_consumer_role}",
    "${var.java_runtime_role}"]
  vars {
    ansible_host = "${digitalocean_droplet.cache_consumer_01_droplet.ipv4_address}"
    ansible_python_interpreter = "${var.ansible_python_interpreter}"
    datacenter_name = "${var.primary_datacenter_name}"
    datacenter_role = "${var.primary_datacenter_role}"
  }
}

# producer
resource "digitalocean_droplet" "cache_producer_01_droplet" {
  image = "${var.droplet_image}"
  name = "${var.cache_producer_role}-01"
  region = "${var.primary_datacenter_name}"
  size = "${var.droplet_size}"
  private_networking = true
  ssh_keys = [
    "${var.ssh_fingerprint}"]
  tags = [
    "${var.target_env}",
    "${var.cache_client_role}",
    "${var.cache_producer_role}",
    "${var.discovery_client_role}",
    "${var.java_runtime_role}"]
}

resource "ansible_host" "cache_producer_01_droplet" {
  inventory_hostname = "${digitalocean_droplet.cache_producer_01_droplet.name}"
  groups = [
    "${var.target_env}",
    "${var.discovery_client_role}",
    "${var.cache_client_role}",
    "${var.cache_producer_role}",
    "${var.java_runtime_role}"]
  vars {
    ansible_host = "${digitalocean_droplet.cache_producer_01_droplet.ipv4_address}"
    ansible_python_interpreter = "${var.ansible_python_interpreter}"
    datacenter_name = "${var.primary_datacenter_name}"
    datacenter_role = "${var.primary_datacenter_role}"
  }
}

