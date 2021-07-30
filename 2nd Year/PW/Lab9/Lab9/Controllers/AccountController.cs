using Lab9.DataAbstractionLayer;
using Lab9.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Web.Security;
using System.Web.UI;

namespace Lab9.Controllers
{
    public class AccountController : Controller
    {
        // GET: Account
        [Authorize]
        public ActionResult Index()
        {
            return View();
        }

        public ActionResult LogIn()
        {
            return View();
        }


        [HttpPost]
        public ActionResult LogIn(User user, string returnUrl)
        {
            DAL dal = new DAL();
            string username = Request.Params["username"];
            string password = Request.Params["password"];
            string submit = Request.Params["submit"];
            
            FormsAuthentication.SetAuthCookie(username, false);
            User user2 = GetUser(username);

            if (submit == "login")
            {
                bool dataItem = dal.CheckCredentials(username, password);
                if (dataItem) {
                    TempData["msg"] = "";
                    Session["username"] = username;
                return RedirectToAction("Profile", "Account");
                }
                else
                {
                    TempData["msg"] = "Wrong password or username";
                    Session.Abandon();
                    return View();
                }
                //string new_password = Request.Params["new_password"];
                //if (dal.ChangePassword(password, new_password, username))
                //{
                //    TempData["msg"] = "<script>alert('Change successful');</script>";

                //    return View("Profile");
                //}
                //TempData["msg"] = "<script>alert('Wrong password');</script>";
                //return View("Profile");
            }

            //if (dataItem)
            //{
            //    Session["username"] = username;
            //    Session["email"] = user2.email;
            //    Session["age"] = user2.age;
            //    Session["webpage"] = user2.webpage;
            //    //FormsAuthentication.SetAuthCookie(username, false);
            //    return RedirectToAction("Profile", "Account");

            //}
            //return Redirect(returnUrl);
            else {
                TempData["msg"] = "Wrong password or username";
                Session.Abandon();
                return View(); }
        }

        public ActionResult SignUp()
        {
             return View();
        }

        [HttpPost]
        public ActionResult SignUp(string returnUrl)
        {
            try
            {
                DAL dal = new DAL();
                string username = Request.Params["username"];
                string password = Request.Params["password"];
                string email = Request.Params["email"];
                string webpage = Request.Params["webpage"];
                string role = Request.Params["role"];
                int age = int.Parse(Request.Params["age"]);
                User user = new User(username, password, age, email, webpage, role);

                if (age < 18)
                {
                    Session.Abandon();
                    var page = HttpContext.CurrentHandler as Page;
                    ScriptManager.RegisterStartupScript(page, page.GetType(), "alert", "alert('wrong');window.location ='Account/SignUp';", true);
                    TempData["msg"] = "Age should be 18 or higher";
                    return View();
                }

                bool dataItem = dal.SaveUser(user);
                FormsAuthentication.SetAuthCookie(username, false);
                Session["username"] = username;

                
                if (dataItem)
                {
                    Session["username"] = username;
                    Session["email"] = email;
                    Session["age"] = age;
                    Session["webpage"] = webpage;
                    TempData["msg"] = "";
                    return RedirectToAction("Profile", "Account");

                }
                //return Redirect(returnUrl);
                else
                {
                    TempData["msg"] = "Username or email in use";
                    Session.Abandon();
                    return View();
                }
            }
            catch(Exception ex)
            {
                Session.Abandon();
                return View();
            }
        }
        [Authorize]
        public ActionResult SignOut()
        {
            FormsAuthentication.SignOut();
            Session.Abandon();
            TempData["msg"] = "";
            return RedirectToAction("LogIn", "Account");
        }
        [HttpPost]
        public User GetUser(string username)
        {
            DAL dal = new DAL();
            //string username = Request.Params["username"];
            return dal.GetUser(username);
            //return dal.GetUser(Request.Params["username"]);
        }

        
        public string GetUserDetails()
        {

            DAL dal = new DAL();
            string username = Request.Params["username"];
            string submit = Request.Params["submit"];
            User user = dal.GetUser(username);
            string output = "<h1>Details</h1>";
            output += "<p>Email: " + user.email + "</p>" +
                "<p>Age: " + user.age + "</p>" +
                "<p>Webpage: " + user.webpage + "</p>" +
                "<p>Role: " + user.role + "</p>";
            output += TempData["msg"];
            return output;
        }

        public ActionResult Profile()
        {
            return View("Profile");
        }

        [HttpPost]
        public ActionResult Profile(string returnUrl)
        {
            DAL dal = new DAL();
            string submit = Request.Params["submit"];
            string username = (string)Session["username"];
            string password = Request.Params["password"];
            

            if (submit == "change_password")
            {
                string new_password = Request.Params["new_password"];
                if (dal.ChangePassword(password, new_password, username))
                {
                    TempData["msg"] = "<script>alert('Change successful');</script>";

                    return View("Profile");
                }
                TempData["msg"] = "<script>alert('Wrong password');</script>";
                return View("Profile");
            }
            if (submit == "change_username")
            {
                string new_username = Request.Params["new_username"];
                if (dal.ChangeUsername(username, new_username, password))
                {
                    TempData["msg"] = "<script>alert('Change successful');</script>";
                    Session["username"] = username;
                    return View("Profile");
                }
                TempData["msg"] = "<script>alert('Wrong password or username in use');</script>";

                return View("Profile");

            }
            if (submit == "change_details")
            {
                try
                {
                    string email = Request.Params["email"];
                    string webpage = Request.Params["webpage"];
                    int age = int.Parse(Request.Params["age"]);
                    string pass_details = Request.Params["password_details"];
                    if (dal.ChangeDetails(username, pass_details, email, webpage, age))
                    {
                        TempData["msg"] = "<script>alert('Change successful');</script>";
                        Session["email"] = email;
                        Session["age"] = age;
                        Session["webpage"] = webpage;
                        return View("Profile");
                    }
                    TempData["msg"] = "<script>alert('Wrong password or email in use');</script>";
                    return View("Profile");
                }
                catch (Exception ex)
                {
                    TempData["msg"] = "<script>alert('Age must be a number');</script>";
                }

            }
            if (submit == "delete")
            {
                if (dal.DeleteAccout(username, password))
                {
                    Session.Abandon();
                    TempData["msg"] = "<script>alert('Account deleted');</script>";
                    return View("LogIn");
                }
                TempData["msg"] = "<script>alert('Wrong password');</script>";
                return View("Profile");

            }
            else return View("Profile");
        }

    }
}