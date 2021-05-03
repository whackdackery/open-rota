# open-rota
An open source rota Java service

### Development
Get your database up and running...

```shell
docker run --name open-rota-mysql \
  -e MYSQL_ROOT_PASSWORD=rootpassword \
  -e MYSQL_DATABASE=openrota \
  -e MYSQL_USER=rotauser \
  -e MYSQL_PASSWORD=rotapass \
  -p 3306:3306 \
  -d mysql:8
 ```
