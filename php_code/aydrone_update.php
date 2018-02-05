<?php 	

require 'conorcal.php';

	$Droneid = $_POST['droneid'];
	$Dronecolor = $_POST['dronecolor'];
    $Droneweight=$_POST['droneweight'];
    $Droneactivation='是';

if (!empty($_POST['droneid'])) {
	# code...
	$query = 'Update AYDRONE_AIR set activation=:Droneactivation,color=:Dronecolor,weight=:Droneweight where drone_id=:Droneid'; 
    //$query = 'Delete FROM AYDRONE_AIR where personname = :personname'; 
	$stid = oci_parse($conn, $query) ;// 编译SQL语句，准备执行 

	oci_bind_by_name($stid, ":Droneid", $Droneid);//设置绑定变量的取值
    oci_bind_by_name($stid, ":Droneactivation", $Droneactivation);
    oci_bind_by_name($stid, ":Dronecolor", $Dronecolor);
    oci_bind_by_name($stid, ":Droneweight", $Droneweight);

    }else {
    	# code...
    	echo "0";
    }



//$stid = oci_parse($conn, $query) ;// 编译SQL语句，准备执行 
if (!$stid) { 
$e = oci_error($conn); 
echo($e['message']); 
exit; 
} 


$r = oci_execute($stid);

//$r = oci_execute($stid, OCI_DEFAULT); // 执行SQL。OCI_DEFAULT表示不要自动commit 
if(!$r) { 
$e = oci_error($stid); 
//echo ($e['message']); 
echo "0";
exit; 
}else{
    echo "1";
}



oci_free_statement($stid);  //释放关联于语句或游标的所有资源
oci_close($conn);   //关闭 Oracle数据库连接  


 ?>