# Filename: .bashrc
# Description: Sources in on the class MASTER version for settings information
# 
# Please (DO NOT) edit this file unless you are sure of what you are doing.
# This file and other dotfiles have been written to work with each other.
# Any change that you are not sure off can break things in an unpredicatable
# ways.

# Set the Class MASTER variable and source the class master version of .cshrc

[[ -z ${MASTER} ]] && MASTER=$(echo ${USER} | sed '/[0-9][a-z][a-z]$/s/..$//' | sed 's/[_-]..$//')
[[ -z ${MASTERDIR} ]] && MASTERDIR=$(eval echo ~${MASTER})

export MASTER
export MASTERDIR

[[ -e ${MASTERDIR}/adm/class.bashrc ]] && . "${MASTERDIR}/adm/class.bashrc"

for file in ${HOME}/.bashrc.d/* ; do
	[[ -x ${file} ]] && . "${file}"
done
unset file
