﻿namespace Lab1
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
            this.dataGridCollections = new System.Windows.Forms.DataGridView();
            this.dataGridExhibits = new System.Windows.Forms.DataGridView();
            this.button1 = new System.Windows.Forms.Button();
            this.button2 = new System.Windows.Forms.Button();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridCollections)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridExhibits)).BeginInit();
            this.SuspendLayout();
            // 
            // dataGridCollections
            // 
            this.dataGridCollections.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridCollections.Location = new System.Drawing.Point(12, 33);
            this.dataGridCollections.Name = "dataGridCollections";
            this.dataGridCollections.RowHeadersWidth = 51;
            this.dataGridCollections.RowTemplate.Height = 24;
            this.dataGridCollections.Size = new System.Drawing.Size(520, 184);
            this.dataGridCollections.TabIndex = 0;
            // 
            // dataGridExhibits
            // 
            this.dataGridExhibits.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridExhibits.Location = new System.Drawing.Point(12, 275);
            this.dataGridExhibits.Name = "dataGridExhibits";
            this.dataGridExhibits.RowHeadersWidth = 51;
            this.dataGridExhibits.RowTemplate.Height = 24;
            this.dataGridExhibits.Size = new System.Drawing.Size(775, 220);
            this.dataGridExhibits.TabIndex = 1;
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
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(13, 10);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(76, 17);
            this.label1.TabIndex = 4;
            this.label1.Text = "Collections";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(9, 236);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(56, 17);
            this.label2.TabIndex = 5;
            this.label2.Text = "Exhibits";
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(800, 507);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.button2);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.dataGridExhibits);
            this.Controls.Add(this.dataGridCollections);
            this.Name = "Form1";
            this.Text = "Form1";
            ((System.ComponentModel.ISupportInitialize)(this.dataGridCollections)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridExhibits)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.DataGridView dataGridCollections;
        private System.Windows.Forms.DataGridView dataGridExhibits;
        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.Button button2;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
    }
}

