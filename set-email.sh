#!/bin/bash
while true; do
  while true; do
    read -p "Enter FROM email: " fromEmail
    echo

    if [[ "$fromEmail" =~ ^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$ ]]; then
      echo "Email address $fromEmail is valid."
      break
    else
      echo "Email address $fromEmail is invalid. Please, try again"
    fi
  done

  while true; do
    read -p "Enter TO email: " toEmail
    echo
    if [[ "$toEmail" =~ ^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$ ]]; then
      echo "Email address $toEmail is valid."
      break
    else
      echo "Email address $toEmail is invalid.  Please, try again"
    fi
  done

  if [[ "$fromEmail" != "$toEmail" ]]; then
    echo "Email address $fromEmail and $toEmail are different. It's correct"
    break
  else
    echo "Email address $fromEmail and $toEmail are equals. It's incorrect. Please, try again"
  fi

done

currentFromEmail=$(grep -i from alertmanager.yml | cut -d':' -f 2 | tr -d '[:space:]')
currentToEmail=$(grep -i to alertmanager.yml | cut -d':' -f 2 | tr -d '[:space:]')

echo "Replace (FROM) $currentFromEmail to $fromEmail"
sed -i "s/$currentFromEmail/$fromEmail/" alertmanager.yml

echo "Replace (TO) $currentToEmail to $toEmail"
sed -i "s/$currentToEmail/$toEmail/" alertmanager.yml
