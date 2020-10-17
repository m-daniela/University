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

namespace Lab1
{
    public partial class Form1 : Form
    {
        SqlConnection connection;
        // parent, child
        SqlDataAdapter collections_data, exhibits_data;
        DataSet ds;
        SqlCommandBuilder cmd;
        BindingSource collections_bind, exhibits_bind;

        public Form1()
        {
            InitializeComponent();
        }


        private void Button1_Click(object sender, EventArgs e)
        {
            // create the connection
            connection = new SqlConnection("Data Source = DESKTOP-7807JND\\SQLEXPRESS; Initial Catalog = Museum; Integrated Security = True");
            ds = new DataSet();

            // get the data from the tables
            exhibits_data = new SqlDataAdapter("select * from Exhibits", connection);
            collections_data = new SqlDataAdapter("select * from Collections", connection);

            cmd = new SqlCommandBuilder(exhibits_data);

            exhibits_data.Fill(ds, "Exhibits");
            collections_data.Fill(ds, "Collections");

            // define the relation btw the tables

            DataRelation rel = new DataRelation("FK__Exhibits__col_na__5441852A", ds.Tables["Collections"].Columns["col_name"], ds.Tables["Exhibits"].Columns["col_name"]);
            ds.Relations.Add(rel);

            // bindings

            collections_bind = new BindingSource();
            collections_bind.DataSource = ds;
            collections_bind.DataMember = "Collections";

            exhibits_bind = new BindingSource();
            exhibits_bind.DataSource = collections_bind;
            exhibits_bind.DataMember = "FK__Exhibits__col_na__5441852A";

            // populate with data on click

            dataGridCollections.DataSource = collections_bind;
            dataGridExhibits.DataSource = exhibits_bind;

        }

        private void Button2_Click(object sender, EventArgs e)
        {
            // update data in the child table (Exhibits) 
            // after performing insert/ update/ delete

            exhibits_data.Update(ds, "Exhibits");
        }

    }
}

