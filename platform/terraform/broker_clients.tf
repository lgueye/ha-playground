# broker clients vars
variable "broker_client_role" {
  default = "broker-client"
}
variable "broker_consumer_role" {
  default = "broker-consumer"
}
variable "broker_producer_role" {
  default = "broker-producer"
}

resource "digitalocean_tag" "broker_client_role" {
  name = "${var.broker_client_role}"
}
resource "digitalocean_tag" "broker_consumer_role" {
  name = "${var.broker_consumer_role}"
}
resource "digitalocean_tag" "broker_producer_role" {
  name = "${var.broker_producer_role}"
}

# broker client droplets and ansible inventory

# consumer
resource "digitalocean_droplet" "broker_consumer_droplet" {
  image = "${var.droplet_image}"
  name = "${var.broker_consumer_role}"
  region = "${var.primary_datacenter_name}"
  size = "${var.droplet_size}"
  private_networking = true
  ssh_keys = ["${var.ssh_fingerprint}"]
  tags = ["${var.target_env}","${var.broker_client_role}","${var.broker_consumer_role}","${var.discovery_client_role}"]
}

resource "ansible_host" "broker_consumer_droplet" {
  inventory_hostname = "${digitalocean_droplet.broker_consumer_droplet.name}"
  groups = [
    "${var.target_env}",
    "${var.discovery_client_role}",
    "${var.broker_client_role}",
    "${var.broker_consumer_role}"]
  vars {
    ansible_host = "${digitalocean_droplet.broker_consumer_droplet.ipv4_address}"
    ansible_python_interpreter = "${var.ansible_python_interpreter}"
    datacenter_name = "${var.primary_datacenter_name}"
    datacenter_role = "${var.primary_datacenter_role}"
  }
}

# producer
resource "digitalocean_droplet" "broker_producer_droplet" {
  image = "${var.droplet_image}"
  name = "${var.broker_producer_role}"
  region = "${var.primary_datacenter_name}"
  size = "${var.droplet_size}"
  private_networking = true
  ssh_keys = ["${var.ssh_fingerprint}"]
  tags = ["${var.target_env}","${var.broker_client_role}","${var.broker_producer_role}","${var.discovery_client_role}"]
}

resource "ansible_host" "broker_producer_droplet" {
  inventory_hostname = "${digitalocean_droplet.broker_producer_droplet.name}"
  groups = ["${var.target_env}","${var.discovery_client_role}","${var.broker_client_role}","${var.broker_producer_role}"]
  vars {
    ansible_host = "${digitalocean_droplet.broker_producer_droplet.ipv4_address}"
    ansible_python_interpreter = "${var.ansible_python_interpreter}"
    datacenter_name = "${var.primary_datacenter_name}"
    datacenter_role = "${var.primary_datacenter_role}"
  }
}
