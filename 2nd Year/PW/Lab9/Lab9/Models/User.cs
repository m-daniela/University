using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Lab9.Models
{
    public class User
    {
        public int id_user { get; set; }
        public string username { get; set; }
        public string password { get; set; }

        public int age { get; set; }
        public string email { get; set; }

        public string webpage { get; set; }
        public string role { get; set; }

        public User() { }

        public User(string username, string password, int age, string email, string webpage, string role)
        {
            this.username = username;
            this.password = password;
            this.age = age;
            this.email = email;
            this.webpage = webpage;
            this.role = role;
        }

        public enum Roles
        {
            Manager, CEO, Sales, Support
        }

    }
}