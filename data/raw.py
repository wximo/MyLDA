import os
import jieba.posseg as pseg
import codecs
def readAndwrite(path,path1):

    fileList=[]
    files=os.listdir(path)
    for f in files:
        if(os.path.isdir(path+'/'+f)):
            pass
        if(os.path.isfile(path+'/'+f)):
            fileList.append(f)
            a=path+'/'+f
            file_object=open(a,'r')
            try:
                all_the_text=file_object.read()
                att=pseg.cut(all_the_text)
                output=codecs.open(path1+'/'+f,'w','utf-8')
                for w in att:
                    if w.flag[0]=='n':
                        output.write(w.word+" ")
                output.close()
            finally:
                file_object.close()


if __name__ == '__main__':  
    Path='/host/Users/Administrator.Y26OC25YCRAPBLH/workspace/MyLDA/TrainData'
    path2='/host/Users/Administrator.Y26OC25YCRAPBLH/workspace/MyLDA/Data'
    readAndwrite(Path,path1)


    
