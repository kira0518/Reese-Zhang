dos2unix self_404_status_expected.out
cd ..
python3 webserv.py config.cfg &
PID=$!
cd -
sleep 0.2
result=`curl -s -I 127.0.0.1:8070/missing.html | diff - self_404_status_expected.out`
echo $result
if [ "$result" == "" ]
then
    echo "pass"
else
    echo "fail"
fi

pkill python
