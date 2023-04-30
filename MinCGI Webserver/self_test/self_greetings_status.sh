dos2unix self_greetings_status_expected.out
cd ..
python3 webserv.py config.cfg &
PID=$!
cd -
sleep 0.2
result=`curl -s 127.0.0.1:8070/greetings.html | diff - self_greetings_expected.out`
echo $result
if [ "$result" == "" ]
then
    echo "pass"
else
    echo "fail"
fi

pkill python
