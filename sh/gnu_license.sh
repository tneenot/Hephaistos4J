#!/bin/sh

# This script is a simple way to manage the gnu license file into a text file.
# Author: Tioben Neenot
# Date: Sun Dec 28th 2014

# ######### Begin section: Global variables
#LICENSE_DIR=/etc/license
LICENSE_DIR=../etc
LICENSE_PATH=$LICENSE_DIR/gnu_license.txt
LICENSE_PART="the Free Software Foundation, Inc.,"
EXCLUDE_LIST=$LICENSE_DIR/excludes
MAPPING_LIST=$LICENSE_DIR/mapping
# ######### End section: Global variables

# ########## Begin section: functions
# Shows the online help
help()
{
cat <<EOF
Controls if $LICENSE_PATH is including into the given file. If not, include the $LICENSE_PATH content into the file.
Usage:
   `basename $0` [-?|--help] file

Parameters:
  [-?|--help]: shows this help

  file: file in which the $LICENSE_PATH will be included.
EOF
}

# Gets the file type for the given file. File type is defined thanks to file command and the mapping file content. If no mapping was found, so the file type given by 'file' command will be returned.
# Parameter:
#		$1: file type
# Return:
#		The file type
get_file_type()
{	
	local file_type=`file -ib $1 | cut -f1 -d';' | cut -f2 -d'/'`
	local filename=`basename $1`
	local extension_file=`echo $filename | cut -f2 -d'.'` 
	local mapping_value=`grep $extension_file $MAPPING_LIST | head -n1 | cut -f2 -d'='`
	
	[ "$mapping_value" = "" ] && echo $file_type || echo $mapping_value
}

# Reads the file rights for the file given as parameter. Wrote on the standard output the chmod command to execture to retreive same rights on this file
build_chmod_cmd_on_file()
{
local cmd="chmod"

local user=`ls -l $1 | cut -f1 -d' ' | cut -f2- -d'-' | cut -b1-3 | sed "s/-//g"`
[ "$user" != "" ] && user="u+$user" || user="u-rwx"

local group=`ls -l $1 | cut -f1 -d' ' | cut -f2- -d'-' | cut -b4-6 | sed "s/-//g"`
[ "$group" != "" ] && group="g+$group" || group="g-rwx"

local user_sep=""
[ "$user" != "" -a "$group" != "" ] && user_sep=","

local others=`ls -l $1 | cut -f1 -d' ' | cut -f2- -d'-' | cut -b7-9 | sed "s/-//g"`
[ "$others" != "" ] && others="o+$others" || others="o-rwx"

local group_sep=""
[ "$group" != "" -a "$others" != "" ] && group_sep=","

echo "$cmd $user$user_sep$group$group_sep$others $1"

}

# Creates the first part of the $src_file file which is composing from begin of file until the $begin_line.
create_first_part()
{
 head -n$begin_line $src_file > $1.1
}

# Creates the license part file which is composing of the /etc/license.txt file content with the $line_mark for each line of the license file.
create_license_part()
{
 local date=`date +'%Y'`
 [ "$header_mark" != "" ] &&  echo "$header_mark" >> $1.1
 while read line;
 do
	line=`echo $line | sed "s/#date/$date/"`
   echo "$line_mark $line" >> $1.1
 done < $LICENSE_PATH
 [ "$footer_mark" != "" ] && echo "$footer_mark" >> $1.1
}

# Creates the left part of the $src_file which is composing of the left of file.
create_left_part()
{
 tail -n$(($nb_lines - $begin_line)) $src_file >> $1.2
}

# Assembles all parts of $src_file and remove temporaries files.
assemble_parts_file()
{
  cat $1.1 $1.2 > $1
  rm -f $1.1 $1.2
}    
# ########## End section: functions


# ############ Begin Section: Main
[ "$1" = "-?" -o "$1" = "--help" ] && help && exit 0
[ "$1" = "" ] && echo "No file given as argument" && exit 1

# Read path and name for the file and control if license part exists or not.
src_file=$1
excludes_list=`cat $EXCLUDE_LIST | tr '\n' '|' | sed "s/|$//"`
files_list=`echo $src_file | grep -v -E "$excludes_list"`
nb_lic_lines=`wc -l $LICENSE_PATH | cut -f1 -d' '`

# Control if file is containing the GNU license declaration
grep -q "$LICENSE_PART" $src_file
if [ $? -ne 0 ]; then
	header_mark=""
	footer_mark=""
	line_mark=""
	begin_line=1
	
	# It's necessary to add the license file declaration to the header of the file
	# According to the type of file, the license declaration must be included between header mark    	
	file_type=`get_file_type $src_file`
	if [ ! -f $LICENSE_DIR/$file_type ]; then
		echo "License markers lines not defined for file type: $file_type"
	else
		. $LICENSE_DIR/$file_type		

			nb_lines=`wc -l $src_file | cut -f1 -d' '`
			tmp_file=/tmp/`basename $src_file`

			file_rights_args=`build_chmod_cmd_on_file $src_file`

			create_first_part $tmp_file
			create_license_part $tmp_file
			create_left_part $tmp_file
			assemble_parts_file $tmp_file  

			# Replace the orignal file with the new one
			mv $tmp_file $src_file

			# Fix original rights on this new file release
			`$file_rights_args`
	fi
fi

# ############ End Section: Main


