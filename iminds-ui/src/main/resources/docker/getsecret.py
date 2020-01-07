import json
import requests
import sys

if(len(sys.argv) != 7):
	print('Valid Sytex:- filename.py --ip:<IP> --port:<PORT> --realm:<REALM_NAME> --clientname:<CLIENT_NAME>')
	sys.exit()


ip=sys.argv[1].split(':')[1]
port=sys.argv[2].split(':')[1]
realname=sys.argv[3].split(':')[1]
clientname=sys.argv[4].split(':')[1]
username=sys.argv[5].split(':')[1]
password=sys.argv[6].split(':')[1]
ipport=ip+':'+port


headers = {
    'Content-type': 'application/x-www-form-urlencoded',
}

payload = {'username':username,'password':password,'grant_type':'password','client_id':'admin-cli'}
url='http://'+ipport+'/auth/realms/master/protocol/openid-connect/token'

r = requests.post(url, data=payload, headers=headers)

key=r.json()
token = key['access_token']
bearerToken='Bearer '+token


headers = {
	'Authorization': bearerToken,'Accept':'application/json',
}

payload = 'http://'+ipport+'/auth/admin/realms/'+realname+'/clients'

resp=requests.get(payload,headers=headers)


clients=resp.json()
clientId=''

for client in clients:
	if(client['clientId']==clientname):
		clientId=client['id']

payload='http://'+ipport+'/auth/admin/realms/'+realname+'/clients/'+clientId+'/client-secret'


resp=requests.get(payload,headers=headers)
secret=resp.json()
print(secret['value'])
