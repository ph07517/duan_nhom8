<?php
include "connect.php";
$mangsanphammoi = array();
$query = "SELECT * FROM sanpham ORDER BY ID DESC LIMIT 6";
$data = mysqli_query($conn,$query);
while($row= mysqli_fetch_assoc($data)){
    array_push($mangsanphammoi,new Sanphammoinhat(
        $row['id'],
        $row['tensanpham'],
        $row['giasanpham'],
        $row['hinhanhsanpham'],
        $row['motasanpham'],
        $row['idsanpham']

    ));
}
echo json_encode($mangsanphammoi);
class Sanphammoinhat{
    
function Sanphammoinhat($id,$tensp,$giasp,$hinhanhsp,$motasp,$idsp){
         $this->id=$id;
         $this->tensp=$tensp;
         $this->giasp=$giasp;
         $this->hinhanhsp=$hinhanhsp;
         $this->motasp=$motasp;
         $this->idsp=$idsp;
}
}
?>