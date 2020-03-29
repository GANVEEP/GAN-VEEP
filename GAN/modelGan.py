import tensorflow as tf
import numpy as np
import matplotlib.pyplot as plt
import math
from sklearn import preprocessing

def weight_var(shape, name):
    return tf.get_variable(name=name, shape=shape, initializer=tf.contrib.layers.xavier_initializer())


def bias_var(shape, name):
    return tf.get_variable(name=name, shape=shape, initializer=tf.constant_initializer(0))
def readline(txt_file):
    i = 0
    linenum=0
    y=list()
    xx = list()
    yy = list()
    with open(txt_file, 'r') as f:
        for label in f:
            # print(label)
            i=i+1
            s = label.split("-")
            if len(s)<2:
                continue
            # if i>3:
            #     break
            s1=s[0].split(" ")
            s2=s[1].split(" ")
            xx.append(s1[0])
            xx.append(s1[1])
            xx.append(s1[2])
            xx.append(s1[3])
            xx.append(s1[4])
            xx.append(s1[5])
            xx.append(s1[9])
            y.append(s2[0])
            y.append(s2[1])
            #print(y)
            #yy.append(y)
        f.close()
    xx = np.reshape(xx, (-1, 7))
    yy = np.reshape(y, (-1, 2))
    return xx, yy
#参数各种生成器
G_input = 7
G_label = 2
#判别器参数
D_input = 2
D1_input = 7
D_label = 1
n_hidden = 300
D_hidden = 300
num = 300000
lossnum = 300
Glearning_rate = 0.02
Dlearning_rate = 0.01
Gstddev=0.05
Dstddev=0.05
#labelinput=list()#此处是生成器生成的数据加上原数据，组成了前时刻数据加上预测数据的，假预测数据

x = tf.placeholder(tf.float32, [None, G_input])#代表噪声，也就是输入数据
preY = tf.placeholder(tf.float32, [None, D_input])#代表输入数据的标签
realY = tf.placeholder(tf.float32, [None, D_input])#代表输入数据的标签

Gweights = {#生成网络
    'h1': tf.Variable(tf.truncated_normal([G_input, n_hidden], stddev=Gstddev)),
    'h2': tf.Variable(tf.truncated_normal([n_hidden, n_hidden], stddev=Gstddev)),
    'h3': tf.Variable(tf.truncated_normal([n_hidden, n_hidden], stddev=Gstddev)),
    'h4': tf.Variable(tf.truncated_normal([n_hidden, n_hidden], stddev=Gstddev)),
    'h5': tf.Variable(tf.truncated_normal([n_hidden, n_hidden], stddev=Gstddev)),
    'h6': tf.Variable(tf.truncated_normal([n_hidden, n_hidden], stddev=Gstddev)),
    'hn': tf.Variable(tf.truncated_normal([n_hidden, G_label], stddev=Gstddev))
}
G_W1 = weight_var([G_input, n_hidden], 'G_W1')
G_W2 = weight_var([n_hidden, n_hidden], 'G_W2')
G_W3 = weight_var([n_hidden, n_hidden], 'G_W3')
G_Wn = weight_var([n_hidden, G_label], 'G_Wn')
Gbiasses = {
    'h1': tf.Variable(tf.zeros([n_hidden])),
    'h2': tf.Variable(tf.zeros([n_hidden])),
    'h3': tf.Variable(tf.zeros([n_hidden])),
    'h4': tf.Variable(tf.zeros([n_hidden])),
    'h5': tf.Variable(tf.zeros([n_hidden])),
    'h6': tf.Variable(tf.zeros([n_hidden])),
    'hn': tf.Variable(tf.zeros([G_label]))
}
G_b1 = bias_var([n_hidden], 'G_b1')
G_b2 = bias_var([n_hidden], 'G_b2')
G_b3 = bias_var([n_hidden], 'G_b3')
G_bn = bias_var([G_label], 'G_bn')

D1weights = {#判别网络
    'h1': tf.Variable(tf.truncated_normal([D_input, D_hidden], stddev=Gstddev)),
    'h2': tf.Variable(tf.truncated_normal([D_hidden, D_hidden], stddev=Gstddev)),
    'h3': tf.Variable(tf.truncated_normal([D_hidden, D_hidden], stddev=Gstddev)),
    'h4': tf.Variable(tf.truncated_normal([D_hidden, D_hidden], stddev=Gstddev)),
    'h5': tf.Variable(tf.truncated_normal([D_hidden, D_hidden], stddev=Gstddev)),
    'h6': tf.Variable(tf.truncated_normal([D_hidden, D_hidden], stddev=Gstddev)),
    'hn': tf.Variable(tf.truncated_normal([D_hidden, D_label], stddev=Gstddev))
}
D1_W1 = weight_var([D1_input, D_hidden], 'D1_W1')
D1_W2 = weight_var([D_hidden, D_hidden], 'D1_W2')
D1_W3 = weight_var([D_hidden, D_hidden], 'D1_W3')
D1_Wn = weight_var([D_hidden, D_label], 'D1_Wn')
D1biasses = {
    'h1': tf.Variable(tf.zeros([D_hidden])),
    'h2': tf.Variable(tf.zeros([D_hidden])),
    'h3': tf.Variable(tf.zeros([D_hidden])),
    'h4': tf.Variable(tf.zeros([D_hidden])),
    'h5': tf.Variable(tf.zeros([D_hidden])),
    'h6': tf.Variable(tf.zeros([D_hidden])),
    'hn': tf.Variable(tf.zeros([D_label]))
}
D1_b1 = bias_var([D_hidden], 'D1_b1')
D1_b2 = bias_var([D_hidden], 'D1_b2')
D1_b3 = bias_var([D_hidden], 'D1_b3')
D1_bn = bias_var([D_label], 'D1_bn')

D2weights = {#判别网络
    'h1': tf.Variable(tf.truncated_normal([D_input, D_hidden], stddev=Gstddev)),
    'h2': tf.Variable(tf.truncated_normal([D_hidden, D_hidden], stddev=Gstddev)),
    'h3': tf.Variable(tf.truncated_normal([D_hidden, D_hidden], stddev=Gstddev)),
    'h4': tf.Variable(tf.truncated_normal([D_hidden, D_hidden], stddev=Gstddev)),
    'h5': tf.Variable(tf.truncated_normal([D_hidden, D_hidden], stddev=Gstddev)),
    'h6': tf.Variable(tf.truncated_normal([D_hidden, D_hidden], stddev=Gstddev)),
    'hn': tf.Variable(tf.truncated_normal([D_hidden, D_label], stddev=Gstddev))
}
D2_W1 = weight_var([D_input, D_hidden], 'D2_W1')
D2_W2 = weight_var([D_hidden, D_hidden], 'D2_W2')
D2_W3 = weight_var([D_hidden, D_hidden], 'D2_W3')
D2_Wn = weight_var([D_hidden, D_label], 'D2_Wn')
D2biasses = {
    'h1': tf.Variable(tf.zeros([D_hidden])),
    'h2': tf.Variable(tf.zeros([D_hidden])),
    'h3': tf.Variable(tf.zeros([D_hidden])),
    'h4': tf.Variable(tf.zeros([D_hidden])),
    'h5': tf.Variable(tf.zeros([D_hidden])),
    'h6': tf.Variable(tf.zeros([D_hidden])),
    'hn': tf.Variable(tf.zeros([D_label]))
}
D2_b1 = bias_var([D_hidden], 'D2_b1')
D2_b2 = bias_var([D_hidden], 'D2_b2')
D2_b3 = bias_var([D_hidden], 'D2_b3')
D2_bn = bias_var([D_label], 'D2_bn')

saver = tf.train.Saver()
# 生成器模型


def generation(x):# 生成器
    Glayer_1 = tf.add(tf.matmul(x, G_W1), G_b1)
    Glayer_2 = tf.add(tf.matmul(Glayer_1, G_W2), G_b2)
    Glayer_3 = tf.add(tf.matmul(Glayer_2, G_W3), G_b3)
    G_data = tf.add(tf.matmul(Glayer_3, G_Wn), G_bn)
    G_pred=tf.nn.sigmoid(G_data)
    return G_data  # 返回生成的数据和判别器判别的合成数据


def discriminator(x, y):  # x代表前一时刻的数据，y代表预测数据或者真实数据
    #print(y)
    D1layer_1 = tf.add(tf.matmul(x, D1_W1), D1_b1)
    D1layer_2 = tf.add(tf.matmul(D1layer_1, D1_W2), D1_b2)
    D1layer_3 = tf.add(tf.matmul(D1layer_2, D1_W3), D1_b3)
    D1_data = tf.add(tf.matmul(D1layer_3, D1_Wn), D1_bn)
    # D1_pred = tf.nn.sigmoid(D1_data)

    D2layer_1 = tf.add(tf.matmul(y, D2_W1), D2_b1)
    D2layer_2 = tf.add(tf.matmul(D2layer_1, D2_W2), D2_b2)
    D2layer_3 = tf.add(tf.matmul(D2layer_2, D2_W3), D2_b3)
    D2_data = tf.add(tf.matmul(D2layer_3, D2_Wn), D2_bn)
    # D2_pred = tf.nn.sigmoid(D2_data)
    D_data=D1_data+D2_data
    D_pred = tf.nn.sigmoid(D_data)
    return D_pred, D_data


theta_G = [G_W1, G_W2, G_W3, G_Wn, G_b1, G_b2, G_b3, G_bn]
theta_D1 = [D1_W1, D1_W2, D1_W3, D1_Wn, D1_b1, D1_b2, D1_b3, D1_bn,
            D2_W1, D2_W2, D2_W3, D2_Wn, D2_b1, D2_b2, D2_b3, D2_bn]
# theta_D2 = [D2_W1, D2_W2, D2_W3, D2_Wn, D2_b1, D2_b2, D2_b3, D2_bn]
# labelinput = list()
#生成器模型
# def generation(x):#生成器
#
#     Glayer_1 = tf.add(tf.matmul(x, Gweights['h1']), Gbiasses['h1'])
#     Glayer_2 = tf.add(tf.matmul(Glayer_1, Gweights['h2']), Gbiasses['h2'])
#     Glayer_3 = tf.add(tf.matmul(Glayer_2, Gweights['h3']), Gbiasses['h3'])
#     G_data = tf.add(tf.matmul(Glayer_3, Gweights['hn']), Gbiasses['hn'])
#     G_pred=tf.nn.sigmoid(G_data)
#     return G_data
# #判别器模型
# def discriminator(y):
#     Dlayer_1 = tf.add(tf.matmul(y, Dweights['h1']), Dbiasses['h1'])
#     Dlayer_2 = tf.add(tf.matmul(Dlayer_1, Dweights['h2']), Dbiasses['h2'])
#     Dlayer_3 = tf.add(tf.matmul(Dlayer_2, Dweights['h3']), Dbiasses['h3'])
#     D_data = tf.add(tf.matmul(Dlayer_3, Dweights['hn']), Dbiasses['hn'])
#     D_pred = tf.nn.sigmoid(D_data)
#     return D_pred, D_data
# x代表噪声，X代表噪声产生的真实数据，G_sample代表噪声输入生成器产生的假的真实数据

# labelinput=list()
# labelinput.append(x[0][0])
# labelinput.append(x[0][1])
# labelinput.append(x[0][2])
# labelinput.append(x[0][3])
# labelinput.append(G_sample[0][0])
# labelinput.append(G_sample[0][1])

# labelinput=G_sample[0]+x[0]#把前一时刻的数据和预测的数据连起来#
# labelinput = np.reshape(labelinput, -1)
# labelinput = labelinput.reshape(-1, len(labelinput))
#
# labelinput  = np.array(labelinput).astype('float32')


G_sample = generation(x)
D_real, D_logit_real = discriminator(x, realY)  # 真实样本
# print("------------*************")
# print(labelinput)
D_fake, D_logit_fake = discriminator(x, G_sample)  # 预测的样本
#  生成器使判别真实数据为1的概率增大，使判别假的真实数据为0的概率增大也就是判别假的真实数据为1的概率减小
#  计算loss

# G_loss = tf.reduce_mean(D_logit_real-D_logit_fake)  # 经典loss
# # D_loss = -tf.reduce_mean(D_logit_real-D_logit_fake)

# D_loss = tf.reduce_mean(tf.log(1-D_real) + tf.log(D_fake))
# G_loss = tf.reduce_mean(tf.log(1-D_fake))
# 交叉熵loss
D_loss_real = tf.reduce_mean(tf.nn.sigmoid_cross_entropy_with_logits(
    logits=D_logit_real, labels=tf.ones_like(D_logit_real)))
D_loss_fake = tf.reduce_mean(tf.nn.sigmoid_cross_entropy_with_logits(
    logits=D_logit_fake, labels=tf.zeros_like(D_logit_fake)))
D_loss = D_loss_real + D_loss_fake
G_loss = tf.reduce_mean(tf.nn.sigmoid_cross_entropy_with_logits(
    logits=D_logit_fake, labels=tf.ones_like(D_logit_real)))

#定义生成模型和判别模型的优化器
train_D = tf.train.AdadeltaOptimizer(Dlearning_rate).minimize(D_loss, var_list=theta_D1)
train_G = tf.train.AdadeltaOptimizer(Glearning_rate).minimize(G_loss, var_list=theta_G)
G_aa=1-tf.reduce_mean(tf.abs(realY-G_sample))/(tf.reduce_mean(realY))
loss = tf.reduce_mean(tf.abs(realY-G_sample))
traindata,labeldata=readline("D:\\vehiclelane1\\finish\\okvehicle.txt")#读取训练数据

X1 = traindata[0]
X2 = np.reshape(X1, -1)
X2 = X2.reshape(-1, len(X2))  # Y是真实的数据
X1 = np.array(X2).astype('float32')#x1代表噪音数据，输入生成器产生预测数据

Y1 = labeldata[0]
Y2 = np.reshape(Y1, -1)
Y2 = Y2.reshape(-1, len(Y2))  # Y是真实的数据
Y1 = np.array(Y2).astype('float32')#y1代表与噪音数据相对应的真实数据

sess = tf.InteractiveSession()
sess.run(tf.global_variables_initializer())
saver.restore(sess, "D:\\pythonFirst\\GAN\\checkpoint\\model")#此处是调用模型***实验模型
# saver.restore(sess, "GAN/100/model")#此处是调用模型
# labelinput = list()
i=0
n=1
minloss=999
with open("minloss.txt", 'r') as f:
    for label in f:
        minloss=float(label)
print(minloss)
while i<len(traindata):
    ii=0
    totalloss=0
    totalaa=0
    while ii < 100:
        X11 = traindata[ii]
        X21 = np.reshape(X11, -1)
        X21 = X21.reshape(-1, len(X21))  # Y是真实的数据
        X11 = np.array(X21).astype('float32')  # x1代表噪音数据，输入生成器产生预测数据

        Y11 = labeldata[ii]
        Y21 = np.reshape(Y11, -1)
        Y21 = Y21.reshape(-1, len(Y21))  # Y是真实的数据
        Y11 = np.array(Y21).astype('float32')  # y1代表与噪音数据相对应的真实数据
        G_sample1, loss1,aa = sess.run([G_sample, loss,G_aa], feed_dict={realY: Y11, x: X11})
        totalloss=totalloss+loss1
        totalaa=totalaa+aa
        ii=ii+1
    totalloss=totalloss/100
    totalaa=totalaa/100
    D_loss1, _, D_real1,D_logit_real1, G_sample1 = sess.run([D_loss, train_D, D_real,D_logit_real, G_sample], feed_dict={realY: Y1, x: X1})
    G_loss1, _, D_fake1, D_logit_fake1 = sess.run([G_loss, train_G, D_fake,D_logit_fake], feed_dict={realY: Y1, x: X1})
    # print(G_sample1)
    # print(D_real1)
    # print(D_fake1)
    # print("--------------")
    if i % 100 == 0:
        print('Iter: {}'.format(i))
        print('D fake: '+str(D_fake1[0][0]))
        print('D real: ' + str(D_real1[0][0]))
        print("真实" + str(Y1))
        print("预测" + str(G_sample1))
        print("G_loss:" + str(totalloss))
        print("G_aa:" + str(totalaa))
        print("D_logit_fake: "+str(D_logit_fake1[0][0]))
        print("D_logit_real: "+str(D_logit_real1[0][0]))
        print("---")
    i=i+1
    X1 = traindata[i]
    X2 = np.reshape(X1, -1)
    X2 = X2.reshape(-1, len(X2))  # Y是真实的数据
    X1 = np.array(X2).astype('float32')  # x1代表噪音数据，输入生成器产生预测数据

    Y1 = labeldata[i]
    Y2 = np.reshape(Y1, -1)
    Y2 = Y2.reshape(-1, len(Y2))  # Y是真实的数据
    Y1 = np.array(Y2).astype('float32')  # y1代表与噪音数据相对应的真实数据

    labelinput = list()
    if totalloss<minloss:
        minloss=totalloss
        with open("minloss.txt", "w") as f:
            f.write(str(totalloss))
        saver.save(sess, "D:\\pythonFirst\\GAN\\checkpoint\\model")
        # break
    if i == len(traindata)-1:
        i = 0
    # if G_loss1<2:
    #     saver.save(sess, "GAN/checkpoint/model")
    #     break











