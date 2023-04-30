cd ..
python3 webserv.py config.cfg &
PID=$!
cd -
sleep 0.2s
curl -I 127.0.0.1:8070/megumi.png 2> /dev/null | grep '200 OK' | diff - png_status_expected.out 
kill $PID
