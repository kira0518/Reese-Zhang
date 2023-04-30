dos2unix self_path_expected.out
cd ..
python3 webserv.py config.cfg &
PID=$!
cd ~/assignment-2
sleep 0.2
result=`python3 webserv.py invalid | diff - self_path_expected.out`
echo $result
if [ "$result" == "" ]
then
    echo "pass"
else
    echo "fail"
fi

pkill python
