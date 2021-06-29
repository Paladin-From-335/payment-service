function createUserWithAcc() {
    const firstname = document.getElementById("firstname").value;
    const lastname = document.getElementById("lastname").value;
    const accountNum = document.getElementById("account_num").value;
    const accountType = document.getElementById("account_type").value;
    const balance = document.getElementById("balance").value;
    const responseArea = document.getElementById("acc_resp");

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
                    console.log(response)
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
    const paymentResp = document.getElementById("payment_resp");

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
                    console.log(resp)
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
    const respArea = document.getElementById("journal_resp");

    let data = {
        payerId: payerId,
        receiverId: receiverId,
        sourceAccId: srcAccId,
        destAccId: destAccId
    }

    axios.post("http://localhost:8083/journal", data, {
        headers: {"Content-Type": "application/json"}
    })
        .then((resp) => {
                if (resp.status === 200) {
                    console.log(resp)
                }
            }, (error) => {
                console.log(error)
            }
        )
}

function getAccByUserId() {
    const clientId = document.getElementById("get_acc").value;
    const respArea = document.getElementById("acc_response_area");

    let data = {
        clientId: clientId
    }

    axios.post("http://localhost:8083/accounts", data, {
        headers: {"Content-Type": "application/json"}
    }).then((resp) => {
            if (resp.status === 200) {
                console.log(resp)
            }
        }, (error) => {
            console.log(error)
        }
    )
}

