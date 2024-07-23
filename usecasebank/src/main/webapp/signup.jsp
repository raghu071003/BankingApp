<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Sign Up Page</title>
    <style>
        body {
            background-color: #f0f2f5;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            font-family: 'Arial', sans-serif;
        }

        .login {
            width: 100%;
            max-width: 400px;
            padding: 20px;
            background: #fff;
            border-radius: 12px;
            box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
            text-align: center;
            transition: transform 0.3s ease-in-out;
        }

        .login:hover {
            transform: scale(1.02);
        }

        .login img {
            width: 80px;
            margin-bottom: 20px;
        }

        h2 {
            font-size: 24px;
            color: #333;
            margin-bottom: 20px;
            text-transform: uppercase;
            letter-spacing: 1px;
        }

        .input-group {
            margin-bottom: 15px;
            text-align: left;
        }

        .labels {
            display: block;
            font-size: 14px;
            color: #555;
            margin-bottom: 5px;
        }

        .text {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 6px;
            box-sizing: border-box;
            font-size: 16px;
            outline: none;
            transition: border-color 0.3s ease;
        }

        .text:focus {
            border-color: #007bff;
        }

        .button {
            width: 100%;
            padding: 12px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s ease;
        }

        .button:hover {
            background-color: #0056b3;
        }

        .button:focus {
            outline: none;
        }

        .forgot {
            display: block;
            margin-top: 10px;
            color: #007bff;
            text-decoration: none;
            font-size: 14px;
            transition: color 0.3s ease;
        }

        .forgot:hover {
            color: #0056b3;
        }

        hr {
            border: 0;
            height: 1px;
            background: #ddd;
            margin: 20px 0;
        }

        @media (max-width: 480px) {
            .login {
                padding: 15px;
            }

            .text, .button {
                font-size: 14px;
            }
        }
    </style>
</head>
<body>
    <div class="login">
        <img src="images/1.jpg" alt="Bank Symbol">
        <h2>Register a User</h2>
        <form action="SignupServlet" method="post">
            <div class="input-group">
                <label class="labels" for="full_name">Full Name</label>
                <input type="text" id="full_name" name="full_name" class="text" required>
            </div>
            <div class="input-group">
                <label class="labels" for="address">Address</label>
                <input type="text" id="address" name="address" class="text" required>
            </div>
            <div class="input-group">
                <label class="labels" for="mobile_no">Mobile No</label>
                <input type="text" id="mobile_no" name="mobile_no" class="text" required>
            </div>
            <div class="input-group">
                <label class="labels" for="email_id">Email ID</label>
                <input type="email" id="email_id" name="email_id" class="text" required>
            </div>
            <div class="input-group">
                <label class="labels" for="account_type">Account Type</label>
                <select id="account_type" name="account_type" class="text" required>
                    <option value="Current">Current</option>
                    <option value="Savings">Savings</option>
                </select>
            </div>
            <div class="input-group">
                <label class="labels" for="initial_balance">Initial Balance</label>
                <input type="number" id="initial_balance" name="initial_balance" class="text" required min="1000">
            </div>
            <div class="input-group">
                <label class="labels" for="dob">Date of Birth</label>
                <input type="date" id="dob" name="dob" class="text" required>
            </div>
            <div class="input-group">
                <label class="labels" for="id_proof">ID Proof</label>
                <input type="text" id="id_proof" name="id_proof" class="text" required>
            </div>
            <button type="submit" class="button">Sign Up</button>
            <hr>
            <a href="Customerlogin.jsp" class="forgot">Already have an account? Log in here</a>
        </form>
    </div>
</body>
</html>
