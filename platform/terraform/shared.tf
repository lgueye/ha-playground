# provider variables
variable "do_token" {}
variable "pub_key" {}
variable "pvt_key" {}
variable "ssh_fingerprint" {}

provider "digitalocean" {
  token = "${var.do_token}"
}

# droplets specs
variable "droplet_image" {
  default = "ubuntu-18-04-x64"
}
variable "primary_datacenter_name" {
  default = "fra1"
}
variable "fallback_datacenter_name" {
  default = "ams3"
}
variable "ternary_datacenter_name" {
  default = "lon1"
}
variable "primary_datacenter_role" {
  default = "primary"
}
variable "fallback_datacenter_role" {
  default = "fallback"
}
variable "ternary_datacenter_role" {
  default = "ternary"
}
variable "droplet_size" {
  default = "1gb"
}

# target environment
variable "target_env" {}

resource "digitalocean_tag" "target_env" {
  name = "${var.target_env}"
}

# ansible vars
variable "ansible_python_interpreter" {
  default = "/usr/bin/python3"
}

# discovery shared vars
variable "discovery_client_role" {
  default = "discovery-client"
}
resource "digitalocean_tag" "discovery_client_role" {
  name = "${var.discovery_client_role}"
}

# java shared vars
variable "java_runtime_role" {
  default = "java-runtime"
}
resource "digitalocean_tag" "java_runtime_role" {
  name = "${var.java_runtime_role}"
}
