from flask import Flask
import os
import subprocess
from datetime import datetime
import pytz

app = Flask(__name__)

@app.route('/htop')
def htop():
    name = "Ayush Keshri"  # Replace with your full name
    try:
        username = os.getlogin()
    except:
        username = os.getenv("CODESPACE_NAME", "unknown")
    
    ist_time = datetime.now(pytz.timezone('Asia/Kolkata')).strftime('%Y-%m-%d %H:%M:%S.%f')[:-3]

    top_output = subprocess.getoutput("top -b -n 1 | head -20")

    response = f"""
    <html>
    <body>
        <h2>Name: {name}</h2>
        <h3>user: {username}</h3>
        <h3>Server Time (IST): {ist_time}</h3>
        <h3>TOP output:</h3>
        <pre>{top_output}</pre>
    </body>
    </html>
    """
    return response

if __name__ == "__main__":
    app.run(host='0.0.0.0', port=5000)