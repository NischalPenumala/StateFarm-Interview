#!/bin/ksh

#Simple script to stage or insert the customer id based on the region.

. /home/nischal/bin/profile.sh
. /home/nischal/conf/context.properties

# Passing the customerid, country and incident number as input to the script
gfcid=$1
cntr=$2
inc=$3

# Get the region(ASPAC,EMEA,LATAM,NAM) based on the country and spool the region into txt file
$ORACLE_HOME/bin/sqlplus -s $db_login/$regpwd@$app_sid << !
set pageize 0 feedback off verify heading off echo off feed off

SPOOL region.txt
select cn.region from country cn where cn.cntr_cd='$cntr';
SPOOLOFF  
exit;
!

# Based on the region, connect to region database and insert the values.
for i in 'cat region.txt'
do
	if [$i =  "NA" ]; then
		$ORACLE_HOME/bin/sqlplus -s $db_login/$regpwd@$db_link_na << !
		set pageize 0 feedback off verify heading off echo off feed off
		
		insert into na_reg.rgn_stg_main(gfcid,id,cntr_cd,incident_number)
		select ma.client_id,ma.id,ma.cntr_cd,'$inc' from gbl_main ma where ma.client_id=$gfcid and ma.cntr_cd=$cntr;
	exit;
	!

	elif [$i = "LATAM" ]; then
		$ORACLE_HOME/bin/sqlplus -s $db_login/$regpwd@$db_link_latam << !
		set pageize 0 feedback off verify heading off echo off feed off
		
		insert into na_reg.rgn_stg_main(gfcid,id,cntr_cd,incident_number)
		select ma.client_id,ma.id,ma.cntr_cd,'$inc' from gbl_main ma where ma.client_id=$gfcid and ma.cntr_cd=$cntr;
	exit;
	!
	
	elif [$i = "APAC" ]; then
		$ORACLE_HOME/bin/sqlplus -s $db_login/$regpwd@$db_link_apac << !
		set pageize 0 feedback off verify heading off echo off feed off
		
		insert into na_reg.rgn_stg_main(gfcid,id,cntr_cd,incident_number)
		select ma.client_id,ma.id,ma.cntr_cd,'$inc' from gbl_main ma where ma.client_id=$gfcid and ma.cntr_cd=$cntr;
	exit;
	!
	elif [$i = "EMEA" ]; then
		$ORACLE_HOME/bin/sqlplus -s $db_login/$regpwd@$db_link_emea << !
		set pageize 0 feedback off verify heading off echo off feed off
		
		insert into na_reg.rgn_stg_main(gfcid,id,cntr_cd,incident_number)
		select ma.client_id,ma.id,ma.cntr_cd,'$inc' from gbl_main ma where ma.client_id=$gfcid and ma.cntr_cd=$cntr;
	exit;
	!
	fi
done	
