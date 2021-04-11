# build docker container
sudo docker image build --tag canteenia .

# run image interactive mode
sudo docker run -p 5000:8080 --env-file ./env.list canteenia

# run image detached mode
sudo docker run --detach -p 5000:8080 --env-file ./env.list canteenia