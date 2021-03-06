clc;
v1 = csvread('svdV.csv');

d1 = csvread('doc_term.csv');

projv = [v1(:,1) v1(:,2)];
projmat = d1 * projv;

[IDX, C1, SUMD, D] = kmeans(projmat, 5,'Start','uniform');
color(1) = 'r';
color(2) = 'b';
color(3) = 'g';
color(4) = 'c';
color(5) = 'm';
color(6) = 'y';
color(7) = 'k';
hold on;
for i=1:26573;
    col = sprintf('%s',color(IDX(i)));
    col = strcat(col,'.');
    if projmat (i,2) >= 0
    plot(projmat(i,1),projmat(i,2),col);
    end 
   
end;

plot(C1(1,1),C1(1,2),'ko','MarkerSize',11,'LineWidth',2);
plot(C1(2,1),C1(2,2),'ko','MarkerSize',11,'LineWidth',2);
plot(C1(3,1),C1(3,2),'ko','MarkerSize',11,'LineWidth',2);
plot(C1(4,1),C1(4,2),'ko','MarkerSize',11,'LineWidth',2);
plot(C1(5,1),C1(5,2),'ko','MarkerSize',11,'LineWidth',2);
%plot(C1(6,1),C1(6,2),'ko','MarkerSize',11,'LineWidth',2);
%plot(C1(7,1),C1(7,2),'yo','MarkerSize',11,'LineWidth',2);
plot(C1(1,1),C1(1,2),'kx','MarkerSize',11,'LineWidth',2);
plot(C1(2,1),C1(2,2),'kx','MarkerSize',11,'LineWidth',2);
plot(C1(3,1),C1(3,2),'kx','MarkerSize',11,'LineWidth',2);
plot(C1(4,1),C1(4,2),'kx','MarkerSize',11,'LineWidth',2);
plot(C1(5,1),C1(5,2),'kx','MarkerSize',11,'LineWidth',2);
%plot(C1(6,1),C1(6,2),'kx','MarkerSize',11,'LineWidth',2);
%plot(C1(7,1),C1(7,2),'yx','MarkerSize',11,'LineWidth',2);


xlabel('tweets');
ylabel('two most variant topics');
sse = 'SSE = ';
sse = strcat(sse,sprintf('%f',mean(SUMD)));
%text(2.1,0.75,sse);
text(2.0,2.4,sse);
csvwrite('membership.csv',IDX);
csvwrite('centroids.csv',C1);
saveas(gcf,'cluster-image','jpg');

clustsize = histc(IDX,1:5);
csvwrite('clustersize.csv',clustsize);