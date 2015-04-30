#!/bin/csh

if !($?MASTER) then
    setenv MASTER \
    `echo $USER | sed '/[0-9][a-z][a-z]$/s/..$//' | sed 's/[_-]..$//'`
endif

if (-e ~$MASTER/adm/class.xsession) ~$MASTER/adm/class.xsession

