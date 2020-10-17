using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Configuration;
using System.Collections.Specialized;

namespace Lab1
{
    class ConfigParser
    {
        public string GetConnectionString()
        {
            // connect to the database given in the config file
            return ConfigurationManager.ConnectionStrings["connection"].ConnectionString.ToString();
        }

        public NameValueCollection GetSettings()
        {
            // return a NameValueCollection of the data in the config file
            return ConfigurationManager.AppSettings;
        }

        public void ChangeConfig(string table)
        {
            // change the path to the config file of the appSettings section
            string path = table;
            Configuration config = ConfigurationManager.OpenExeConfiguration(ConfigurationUserLevel.None);
            AppSettingsSection appSettings = config.AppSettings;
            appSettings.File = path;

            // save the changes
            config.Save(ConfigurationSaveMode.Modified);
            ConfigurationManager.RefreshSection("appSettings");

            //MessageBox.Show(appSettings.File);
        }
    }
}
