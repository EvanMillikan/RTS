use std::thread;
use std::time::Duration;

use std::sync::mpsc;
// Exercise 1 : Stopwatch (Main Thread is Minutes and Spawwned Thread is Seconds)
/*fn main(){
    //seconds thread
    thread::spawn(||{
        let mut s =0;
        loop{
            println!("Seconds: {}", s);
            s+=1;
            thread::sleep(Duration::from_secs(1));
        }
    });

    //minutes
    let mut m = 0;
    let mut counter = 0;
    loop{
        counter+=1;
        if counter==60{
            m+=1;
            counter=0;
        }
        print!("Minute: {}:",m);
        thread::sleep(Duration::from_secs(1));
    }

}*/


//Exercise 2 : Stopwatch using two channels to pass values to main thread
/*fn main(){
    
    let (sd1, rv1) = mpsc::channel();
    let (sd2, rv2) = mpsc::channel();


    thread::spawn(move || {
        let mut s = 0;
        loop{
            sd1.send(s).unwrap();
            s+=1;
            if s==60{
                s = 0;
            }
            thread::sleep(Duration::from_secs(1));
        }
    });

    thread::spawn(move || {
        let mut m = 0;
        let mut counter = 0;
        loop{
            if counter==60{
                m+=1;
                counter=0;
            }
            sd2.send(m).unwrap();
            counter+=1;
            thread::sleep(Duration::from_secs(1));
        }
    });

    loop{
        let seconds = rv1.recv().unwrap();
        let minutes = rv2.recv().unwrap();
        println!("{}:{}", minutes, seconds);
    }
}*/


//Homework: Update the Stopwatch to make the seconds thread
//          notify the minute thread that 60 seconds has elapsed
//

fn main(){
    let (sd1, rv1) = mpsc::channel();
    let (sd2, rv2) = mpsc::channel();
    let (sd3, rv3) = mpsc::channel();

    thread::spawn(move || {
        let mut s = 0;
        loop{
            sd1.send(s).unwrap();
            s+=1;
            sd2.send(s).unwrap();
            if s==60{
                s = 0;
            }
            thread::sleep(Duration::from_secs(1));
        }
    });

    thread::spawn(move || {
        let mut m = 0;
        loop{
            sd3.send(m).unwrap();
            let is_increment = rv2.recv().unwrap();
            if is_increment==60{
                m+=1;
            }
            thread::sleep(Duration::from_secs(1));
        }
    });

    loop{
        let seconds = rv1.recv().unwrap();
        let minutes = rv3.recv().unwrap();
        println!("{}:{}", minutes, seconds);
    }

} 