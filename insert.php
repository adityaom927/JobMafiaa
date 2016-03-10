<?php
	
$con=mysql_connect("localhost","root","root");

	if($con==false)

	{
    
		echo mysql_error();

		die();
	}
	

mysql_select_db("phpdb",$con) or die("Database Not Found");


	$q="insert into ads(ad_head,ad_content,ad_contact,ad_image)values('hello ad','ad details here','conatct number','image name.ext')";


	mysql_query($q);

	
$i=mysql_affected_rows($con);

	if($i>0)

	{
    
		echo "Successfully Insered";

	}
	else
	
{
    
		echo "Not";
	
}
?>