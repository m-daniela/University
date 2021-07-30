using Lab9.DataAbstractionLayer;
using Lab9.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Web.Security;

namespace Lab9.Controllers
{
    public class MainController : Controller
    {
        // GET: Main
        public ActionResult Index()
        {
            return View("FilterUsers");
        }

        public ActionResult Profile()
        {
            return View("FilterUsers");
        }

        
        public string FilterUsers()
        {
            string result = "<p id=\"header\"><span>Username</span><span>Email</span><span>Age</span><span>Webpage</span><span>Role</span></p>";
            try
            {
                string input = Request.Params["input"];
                string filter = Request.Params["filter"];

                DAL dal = new DAL();


                List<User> data;
                if(input == "" || input == null)
                {
                    data = dal.GetUsers();
                }
                else if (filter == "age")
                {
                    data = dal.GetUsers(filter, int.Parse(input));
                }
                else
                {
                    data = dal.GetUsers(filter, input);
                }

                
                foreach (User user in data)
                {
                    result += "<p><span><a href=profile.php?username=" + user.username + ">" + user.username + "</a></span>" +
                            "<span>" + user.email + "</span>" +
                            "<span>" + user.age + "</span>" +
                            "<span>" + user.webpage + "</span>" +
                            "<span>" + user.role + "</span></p>";

                }
            }
            catch(Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
            return result;

        }

        [Authorize]
        public ActionResult SignOut()
        {
            Session.Abandon();
            TempData["msg"] = "";
            FormsAuthentication.SignOut();
            return RedirectToAction("LogIn", "Account");
        }
    }
}