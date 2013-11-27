import csv
import sqlite3
import re

conn = sqlite3.connect('database_type_count.db')
c = conn.cursor()
sqliter = "CREATE TABLE IF NOT EXISTS type_tabel_3 (Subject TEXT, Predicate TEXT, Object TEXT)";
c = conn.cursor()
c.execute(sqliter)
conn.commit()
for row2 in c.execute('SELECT * FROM type_tabel_2 ;'):
	if row2[0]:
		#print row2[0]
		matchObj = re.match( r'\[\[.*\]\]', row2[2])
		if matchObj:
			#print row2[2]
			c = conn.cursor()
			sqlQure = 'insert into  type_tabel_3 values(?,?,?);'
			variables = [row2[0] , row2[1] , row2[2]]
			c.execute(sqlQure, variables)
			conn.commit()


	
