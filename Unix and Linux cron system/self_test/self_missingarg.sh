cd ..
dos2unix self_missingarg_expected.out
python3 webserv.py config.cfg &
PID=$!

sleep 0.2
result=`python3 webserv.py | diff - self_missingarg_expected.out`
echo $result
cat self_missingarg_expected.out
if [ "$result" == "" ]
then
    echo "pass"
else
    echo "fail"
fi

pkill python
