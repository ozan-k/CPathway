rm(list = ls());

setwd("/Users/ozan/My/Current/DyHealthNet/CPathway/");

data <- read.csv("sim1b.csv", header=TRUE, sep=",");

d <- data.matrix(data);

plot(d[,1],d[,2], type="l", main="", xlab="time", ylab="species", col="red", ylim=c(0,1000));
legend(x=0.18,y=900, c("X","Y"), cex=0.8, col=c("red","blue"), lty=1:1);
lines(d[,1],d[,3], type="l",col="blue");


rm(list = ls());
data <- read.csv("sim2.csv", header=TRUE, sep=",");

d <- data.matrix(data);

plot(d[,1],d[,3], type="l", main="", xlab="time", ylab="species", col="red",ylim=c(0,100));
legend(x=0.18,y=900, c("X","Y"), cex=0.8, col=c("red","blue"), lty=1:1);
lines(d[,1],d[,6], type="l",col="blue");

