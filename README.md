html
<!DOCTYPE html>
<html>
<head>
    <title>Tic Tac Toe</title>
    <style>
        .board {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            grid-template-rows: repeat(3, 1fr);
            gap: 5px;
            width: 300px;
            height: 300px;
            margin: 0 auto;
        }
        
        .cell {
            display: flex;
            justify-content: center;
            align-items: center;
            font-size: 48px;
            background-color: #eee;
            cursor: pointer;
        }
    </style>
</head>
<body>
    <div class="board">
        <div class="cell"></div>
        <div class="cell"></div>
        <div class="cell"></div>
        <div class="cell"></div>
        <div class="cell"></div>
        <div class="cell"></div>
        <div class="cell"></div>
        <div class="cell"></div>
        <div class="cell"></div>
    </div>
    
    <script>
        const cells = document.querySelectorAll('.cell');
        let currentPlayer = 'X';
        
        cells.forEach(cell => {
            cell.addEventListener('click', handleCellClick);
        });
        
        function handleCellClick() {
            if (this.textContent === '') {
                this.textContent = currentPlayer;
                currentPlayer = currentPlayer === 'X' ? 'O' : 'X';
            }
        }
    </script>
</body>
</html>
