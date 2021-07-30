using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Lab9.Models;
using MySql.Data.MySqlClient;

namespace Lab9.DataAbstractionLayer
{
    public class DAL
    {
        private MySqlConnection conn;
        private void DBConnect()
        {
            string connection_string = "server=localhost;uid=root;pwd=pass;database=enterprise2;";

            conn = new MySqlConnection();

            conn.ConnectionString = connection_string;
            conn.Open();

        }

        private void DBClose()
        {
            conn.Close();
        }
        public List<User> GetUsers()
        {
            List<User> users = new List<User>();

            try
            {
                DBConnect();
                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = conn;
                cmd.CommandText = "select username, email, age, webpage, role from users;";
                

                MySqlDataReader myreader = cmd.ExecuteReader();

                while (myreader.Read())
                {
                    User user = new User();
                    user.username = myreader.GetString("username");
                    user.email = myreader.GetString("email");
                    user.age = myreader.GetInt32("age");
                    user.webpage = myreader.GetString("webpage");
                    user.role = myreader.GetString("role");
                    users.Add(user);
                }
                myreader.Close();
                DBClose();
            }
            catch (MySqlException ex)
            {
                Console.Write(ex.Message);
            }
            return users;
        }

        public List<User> GetUsers(string filter, string input)
        {
            List<User> users = new List<User>();

            try
            {
                DBConnect();
                //string connection_string = "server=localhost;uid=root;pwd=pass;database=enterprise2;";

                //conn = new MySqlConnection();
                //Console.WriteLine(conn);

                //conn.ConnectionString = connection_string;
                //conn.Open();
                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = conn;
                cmd.CommandText = "select username, email, age, webpage, role from users where " + filter + " like @input;";
                //cmd.CommandText = "select u.username, u.email, u.age, u.webpage, u.role from users u where u.username = '" + input + "';";
                //cmd.Parameters.AddWithValue("@filter", filter);
                cmd.Parameters.AddWithValue("@input", input);

                MySqlDataReader myreader = cmd.ExecuteReader();

                while (myreader.Read())
                {
                    User user = new User();
                    user.username = myreader.GetString("username");
                    user.email = myreader.GetString("email");
                    user.age = myreader.GetInt32("age");
                    user.webpage = myreader.GetString("webpage");
                    user.role = myreader.GetString("role");
                    users.Add(user);
                }
                myreader.Close();
                //conn.Close();
                DBClose();
            }
            catch (MySqlException ex)
            {
                Console.Write(ex.Message);
            }
            return users;
        }

        public List<User> GetUsers(string filter, int input)
        {
            List<User> users = new List<User>();

            try
            {
                DBConnect();
                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = conn;
                cmd.CommandText = "select username, email, age, webpage, role from users where " + filter + " = @input;";
                //cmd.Parameters.AddWithValue("@filter", filter);
                cmd.Parameters.AddWithValue("@input", input);

                MySqlDataReader myreader = cmd.ExecuteReader();

                while (myreader.Read())
                {
                    User user = new User();
                    user.username = myreader.GetString("username");
                    user.email = myreader.GetString("email");
                    user.age = myreader.GetInt32("age");
                    user.webpage = myreader.GetString("webpage");
                    user.role = myreader.GetString("role");
                    users.Add(user);
                }
                myreader.Close();
                DBClose();
            }
            catch (MySqlException ex)
            {
                Console.Write(ex.Message);
            }
            return users;
        }

        public bool ChangeUsername(string username, string new_username, string password)
        {
            DBConnect();
            MySqlCommand cmd = new MySqlCommand();
            cmd.Connection = conn;
            cmd.CommandText = "update users " +
                "set username = @new_username " +
                "where username = @username and " +
                "password = @password";
            cmd.Parameters.AddWithValue("@new_username", new_username);
            cmd.Parameters.AddWithValue("@username", username);
            cmd.Parameters.AddWithValue("@password", password);

            int rows = cmd.ExecuteNonQuery();
            DBClose();
            return rows < 1;
        }

        public bool DeleteAccout(string username, string password)
        {
            DBConnect();
            MySqlCommand cmd = new MySqlCommand();
            cmd.Connection = conn;
            cmd.CommandText = "delete from users " +
                "where username = @username and " +
                "password = @password";
            cmd.Parameters.AddWithValue("@username", username);
            cmd.Parameters.AddWithValue("@password", password);

            int rows = cmd.ExecuteNonQuery();
            DBClose();
            return rows == 1;
        }

        public bool ChangeDetails(string username, string password, string email, string webpage, int age)
        {
            try
            {
                DBConnect();
                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = conn;
                cmd.CommandText = "update users " +
                    "set email = @email, " +
                    "webpage = @webpage, " +
                    "age = @age " +
                    "where username = @username and " +
                    "password = @password";
                cmd.Parameters.AddWithValue("@email", email);
                cmd.Parameters.AddWithValue("@webpage", webpage);
                cmd.Parameters.AddWithValue("@age", age);
                cmd.Parameters.AddWithValue("@username", username);
                cmd.Parameters.AddWithValue("@password", password);

                int rows = cmd.ExecuteNonQuery();
                DBClose();
                return rows == 1;
            }
            catch (Exception ex)
            {
                return false;
            }
        }

        public bool SaveUser(User user)
        {
            try
            {
                DBConnect();
                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = conn;
                cmd.CommandText = "insert into users(username, password, age, email, webpage, role) " +
                    "values(@username, @password, @age, @email, @webpage, @role);";
                cmd.Parameters.AddWithValue("@username", user.username);
                cmd.Parameters.AddWithValue("@password", user.password);
                cmd.Parameters.AddWithValue("@age", user.age);
                cmd.Parameters.AddWithValue("@email", user.email);
                cmd.Parameters.AddWithValue("@webpage", user.webpage);
                cmd.Parameters.AddWithValue("@role", user.role);

                int rows = cmd.ExecuteNonQuery();
                DBClose();
                return rows == 1;
            }
            catch (MySqlException ex)
            {
                return false;
            }
        }

        public bool CheckCredentials(string username, string password)
        {
            try
            {
                DBConnect();
                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = conn;
                cmd.CommandText = "select * from users where password = @password and username = @username";
                cmd.Parameters.AddWithValue("@password", password);

                cmd.Parameters.AddWithValue("@username", username);

                MySqlDataReader myreader = cmd.ExecuteReader();

                bool result = myreader.Read();
                DBClose();
                return result;

            }
            catch (MySqlException ex)
            {
                return false;
            }
        }

        public User GetUser(string username)
        {
            DBConnect();
            MySqlCommand cmd = new MySqlCommand();
            cmd.Connection = conn;
            cmd.CommandText = "select username, email, webpage, age, role from users where username = @username";
            cmd.Parameters.AddWithValue("@username", username);
            MySqlDataReader myreader = cmd.ExecuteReader();
            if (myreader.Read())
            {
                User user = new User();
                user.username = myreader.GetString("username");
                user.email = myreader.GetString("email");
                user.age = myreader.GetInt32("age");
                user.webpage = myreader.GetString("webpage");
                user.role = myreader.GetString("role");
                return user;
            }
            return null;
        }
        public bool ChangePassword(string password, string new_password, string username)
        {
            DBConnect();
            MySqlCommand cmd = new MySqlCommand();
            cmd.Connection = conn;
            cmd.CommandText = "update users " +
                "set password = @new_password " +
                "where username = @username and " +
                "password = @password";
            cmd.Parameters.AddWithValue("@new_password", new_password);
            cmd.Parameters.AddWithValue("@password", password);
            cmd.Parameters.AddWithValue("@username", username);
            int rows = cmd.ExecuteNonQuery();
            DBClose();
            return rows == 1;
        }

    }
}