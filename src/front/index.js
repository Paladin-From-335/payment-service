function createUserWithAcc() {
    const firstname = document.getElementById("firstname").value;
    const lastname = document.getElementById("lastname").value;
    const accountNum = document.getElementById("account_num").value;
    const accountType = document.getElementById("account_type").value;
    const balance = document.getElementById("balance").value;
    const responseArea = document.getElementById("resp_margin");

    let data = {
        firstname: firstname,
        lastname: lastname,
        accounts: [{
            accountNum: accountNum,
            accountType: accountType,
            balance: balance
        }
        ]
    }
    axios.post("http://localhost:8083/registration", data, {
        headers: {"Content-Type": "application/json"}
    })
        .then((response) => {
                if (response.status === 200) {
                    responseArea.append(response.data)
                }

            }, (error) => {
                console.log(error.status)
            }
        )
}

function createPayment() {
    const senderId = document.getElementById("sender").value;
    const receiverId = document.getElementById("receiver").value;
    const amount = document.getElementById("amount").value;
    const reason = document.getElementById("reason").value;
    const responseArea = document.getElementById("resp_payment_marg_f");
    const responseArea2 = document.getElementById("resp_payment_marg_s");

    let data = {
        sourceAccId: senderId,
        destAccId: receiverId,
        reason: reason,
        amount: amount
    }

    axios.post("http://localhost:8083/payment", data, {
        headers: {"Content-Type": "application/json"}
    })
        .then((resp) => {
                if (resp.status === 200) {
                    console.log(resp.data)
                    responseArea.append(resp.data.paymentId);
                    responseArea2.append(resp.status)
                }
            }, (error) => {
                console.log(error)
            }
        )
}

function getPaymentJournal() {
    const payerId = document.getElementById("payer_id").value;
    const receiverId = document.getElementById("rec_id").value;
    const srcAccId = document.getElementById("acc_send_id").value;
    const destAccId = document.getElementById("acc_rec_id").value;

    const payment_id = document.getElementById("paym_id_marg");
    const time = document.getElementById("time_marg");
    const src_acc = document.getElementById("src_acc_marg");
    const dest_acc = document.getElementById("dest_acc_marg");
    const amount = document.getElementById("amount_marg");
    const payer = document.getElementById("payer");
    const recipient = document.getElementById("recipient");

    let data = {
        payer_id: payerId,
        receiver_id: receiverId,
        source_acc_id: srcAccId,
        dest_acc_id: destAccId
    }

    axios.post("http://localhost:8083/journal", data, {
        headers: {"Content-Type": "application/json"}
    })
        .then((resp) => {
                if (resp.status === 200) {
                    console.log(resp)
                    payment_id.append(resp.data[0].payment_id);
                    time.append(resp.data[0].timestamp);
                    src_acc.append(resp.data[0].source_acc_num);
                    dest_acc.append(resp.data[0].dest_acc_num);
                    amount.append(resp.data[0].amount);
                    payer.append(resp.data[0].payer.firstname + " ");
                    payer.append(resp.data[0].payer.lastname);
                    recipient.append(resp.data[0].receiver.firstname + " ");
                    recipient.append(resp.data[0].receiver.lastname);
                }
            }, (error) => {
                console.log(error)
            }
        )
}

function getAccByUserId() {
    const clientId = document.getElementById("get_acc").value;
    const acc_id = document.getElementById("account_id");
    const acc_num = document.getElementById("account_number");
    const acc_type = document.getElementById("account_typ");
    const balance = document.getElementById("account_balance");

    let data = {
        clientId: clientId
    }

    axios.post("http://localhost:8083/accounts", data, {
        headers: {"Content-Type": "application/json"}
    }).then((resp) => {
            if (resp.status === 200) {
                console.log(resp)
                acc_id.append(resp.data[0].accountId)
                acc_num.append(resp.data[0].accountNum)
                acc_type.append(resp.data[0].accountType)
                balance.append(resp.data[0].balance)
            }
        }, (error) => {
            console.log(error)
        }
    )
}

