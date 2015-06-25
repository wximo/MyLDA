import os
import jieba.posseg as pseg
import codecs
def readAndwrite(path,path1):

    fileList=[]
    wordsList={}
    wordsAll=[]
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
                
                wt=[]
                for w in att:
                    if w.flag[0]=='n':
                        wt.append(w.word)
                        if wordsList.has_key(w.word):
                            wordsList[w.word]+=1
                        else:
                            wordsList[w.word]=1
                wordsAll.append(wt)
            finally:
                file_object.close()
    j=-1
    for f in files:
        j+=1
        output=codecs.open(path1+'/'+f,'w','utf-8')
        for w in wordsAll[j]:
            if wordsList[w]>10:
                output.write(w+" ")
        output.close()

if __name__ == '__main__':  
    Path='/host/Users/Administrator.Y26OC25YCRAPBLH/workspace/MyLDA/Data6'
    path1='/host/Users/Administrator.Y26OC25YCRAPBLH/workspace/MyLDA/Data61'
    readAndwrite(Path,path1)


    
