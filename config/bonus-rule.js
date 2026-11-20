// Bonus rule (HR can edit this file without recompiling Java)
// salary: monthly salary (yen), evaluation: 1-5
var rate;
if (evaluation >= 5) {
    rate = 3.0;
} else if (evaluation == 4) {
    rate = 2.5;
} else if (evaluation == 3) {
    rate = 2.0;
} else {
    rate = 1.5;
}
Math.floor(salary * rate);
