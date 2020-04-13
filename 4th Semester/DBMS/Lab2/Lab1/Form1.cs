using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Data.SqlClient;
using System.Collections.Specialized;

namespace Lab1
{
    public partial class Form1 : Form
    {
        SqlConnection connection;
        // parent, child
        SqlDataAdapter parent_data, child_data;
        DataSet ds;
        SqlCommandBuilder cmd;
        BindingSource parent_bind, child_bind;

        string connection_string;
        string parent_table;
        string select_from_parent;
        string child_table;
        string select_from_child;
        string relationship_name;
        string relationship_column_parent;
        string relationship_column_child;
        ConfigParser parseConfig;

        public Form1()
        {
            InitializeComponent();
            parseConfig = new ConfigParser();
        }

        private void AssignValues()
        {
            
            try
            {
                dataGridParent.Columns.Clear();
                dataGridChild.Columns.Clear();

                // get and set the configuration file
                parseConfig.ChangeConfig(textBox.Text);

                // set the values
                connection_string = parseConfig.GetConnectionString();
                NameValueCollection settings = parseConfig.GetSettings();
                parent_table = settings["parent"].ToString();
                select_from_parent = settings["parent_data"].ToString();
                child_table = settings["child"].ToString();
                select_from_child = settings["child_data"].ToString();
                relationship_name = settings["relationship_name"].ToString();
                relationship_column_parent = settings["rel_column_parent"].ToString();
                relationship_column_child = settings["rel_column_child"].ToString();
                labelParent.Text = parent_table;
                labelChild.Text = child_table;
            }
            catch (Exception err)
            {
                MessageBox.Show("Error: " + err);
            }
        }


        private void Button1_Click(object sender, EventArgs e)
        {
            try
            {
                AssignValues();
                // create the connection
                connection = new SqlConnection(connection_string);
                ds = new DataSet();

                // get the data from the tables
                child_data = new SqlDataAdapter(select_from_child, connection);
                parent_data = new SqlDataAdapter(select_from_parent, connection);

                cmd = new SqlCommandBuilder(child_data);

                child_data.Fill(ds, child_table);
                parent_data.Fill(ds, parent_table);

                // define the relation btw the tables

                DataRelation rel = new DataRelation(relationship_name, ds.Tables[parent_table].Columns[relationship_column_parent], ds.Tables[child_table].Columns[relationship_column_child]);
                ds.Relations.Add(rel);

                // bindings

                parent_bind = new BindingSource();
                parent_bind.DataSource = ds;
                parent_bind.DataMember = parent_table;

                child_bind = new BindingSource();
                child_bind.DataSource = parent_bind;
                child_bind.DataMember = relationship_name;

                // populate with data on click

                dataGridParent.DataSource = parent_bind;
                dataGridChild.DataSource = child_bind;
            }
            catch (Exception err)
            {
                MessageBox.Show("Error: " + err);
            }

        }

        private void Button2_Click(object sender, EventArgs e)
        {
            // update data in the child table (Exhibits) 
            // after performing insert/ update/ delete
            try {
                child_data.Update(ds, child_table);
            }
            catch (Exception err)
            {
                MessageBox.Show("Error: " + err);
            }
            
        }

    }
}

