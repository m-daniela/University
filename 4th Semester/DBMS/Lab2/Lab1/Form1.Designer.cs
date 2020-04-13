namespace Lab1
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.dataGridParent = new System.Windows.Forms.DataGridView();
            this.dataGridChild = new System.Windows.Forms.DataGridView();
            this.button1 = new System.Windows.Forms.Button();
            this.button2 = new System.Windows.Forms.Button();
            this.labelParent = new System.Windows.Forms.Label();
            this.labelChild = new System.Windows.Forms.Label();
            this.textBox = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridParent)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridChild)).BeginInit();
            this.SuspendLayout();
            // 
            // dataGridParent
            // 
            this.dataGridParent.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridParent.Location = new System.Drawing.Point(12, 33);
            this.dataGridParent.Name = "dataGridParent";
            this.dataGridParent.RowHeadersWidth = 51;
            this.dataGridParent.RowTemplate.Height = 24;
            this.dataGridParent.Size = new System.Drawing.Size(520, 184);
            this.dataGridParent.TabIndex = 0;
            // 
            // dataGridChild
            // 
            this.dataGridChild.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridChild.Location = new System.Drawing.Point(12, 275);
            this.dataGridChild.Name = "dataGridChild";
            this.dataGridChild.RowHeadersWidth = 51;
            this.dataGridChild.RowTemplate.Height = 24;
            this.dataGridChild.Size = new System.Drawing.Size(775, 220);
            this.dataGridChild.TabIndex = 1;
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(555, 33);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(233, 51);
            this.button1.TabIndex = 2;
            this.button1.Text = "Get Data";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.Button1_Click);
            // 
            // button2
            // 
            this.button2.Location = new System.Drawing.Point(554, 165);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(233, 52);
            this.button2.TabIndex = 3;
            this.button2.Text = "Save Changes";
            this.button2.UseVisualStyleBackColor = true;
            this.button2.Click += new System.EventHandler(this.Button2_Click);
            // 
            // labelParent
            // 
            this.labelParent.AutoSize = true;
            this.labelParent.Location = new System.Drawing.Point(13, 10);
            this.labelParent.Name = "labelParent";
            this.labelParent.Size = new System.Drawing.Size(50, 17);
            this.labelParent.TabIndex = 4;
            this.labelParent.Text = "Parent";
            // 
            // labelChild
            // 
            this.labelChild.AutoSize = true;
            this.labelChild.Location = new System.Drawing.Point(9, 236);
            this.labelChild.Name = "labelChild";
            this.labelChild.Size = new System.Drawing.Size(39, 17);
            this.labelChild.TabIndex = 5;
            this.labelChild.Text = "Child";
            // 
            // textBox
            // 
            this.textBox.Location = new System.Drawing.Point(554, 123);
            this.textBox.Name = "textBox";
            this.textBox.Size = new System.Drawing.Size(232, 22);
            this.textBox.TabIndex = 6;
            this.textBox.Text = "table1.config";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(552, 95);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(113, 17);
            this.label3.TabIndex = 7;
            this.label3.Text = "Config file name:";
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(800, 507);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.textBox);
            this.Controls.Add(this.labelChild);
            this.Controls.Add(this.labelParent);
            this.Controls.Add(this.button2);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.dataGridChild);
            this.Controls.Add(this.dataGridParent);
            this.Name = "Form1";
            this.Text = "Form1";
            ((System.ComponentModel.ISupportInitialize)(this.dataGridParent)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridChild)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.DataGridView dataGridParent;
        private System.Windows.Forms.DataGridView dataGridChild;
        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.Button button2;
        private System.Windows.Forms.Label labelParent;
        private System.Windows.Forms.Label labelChild;
        private System.Windows.Forms.TextBox textBox;
        private System.Windows.Forms.Label label3;
    }
}

