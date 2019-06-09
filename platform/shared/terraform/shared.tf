variable "target_env" {}

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
resource "digitalocean_tag" "target_env" {
  name = "${var.target_env}"
}

# ansible vars
variable "ansible_python_interpreter" {
  default = "/usr/bin/python3"
}
