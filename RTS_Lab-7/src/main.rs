// extern crate crossbeam_channel;
// use crossbeam_channel::bounded;
// //use std::sync::mpsc;
// use std::thread;
// use std::time::Duration;

// fn main() {
//     // Rack Channel
//     let (rack_send, rack_rcv) = bounded(12);
//     let rack_send2 = rack_send.clone();
//     // Shelf Channel
//     let (shelf_send, shelf_rcv) = bounded(10);
//     // Baker Thread 1
//     std::thread::spawn(move || loop {
//         for x in 0..10 {
//             let buns = 1;
//             rack_send.send(buns).unwrap();
//             println!("Baker 1 is baking bread");
//             thread::sleep(Duration::from_millis(200));
//         }
//         println!("Baker 1 is now taking a rest for 7 seconds");
//         thread::sleep(Duration::from_secs(7));
//     });

//     // Baker Thread 2
//     std::thread::spawn(move || loop {
//         for x in 0..10 {
//             let buns = 1;
//             rack_send2.send(buns).unwrap();
//             println!("Baker 2 is baking bread");
//             thread::sleep(Duration::from_millis(200));
//         }
//         println!("Baker 2 is now taking a rest for 7 seconds");
//         thread::sleep(Duration::from_secs(7));
//     });

//     // Worker Thread
//     std::thread::spawn(move || loop {
//         let buns = rack_rcv.recv().unwrap();
//         shelf_send.send(buns).unwrap();
//         println!("Worker is moving bread");
//         thread::sleep(Duration::from_secs(1));
//     });

//     // // Simple Customer Loop
//     // let mut c = 1;
//     // loop {
//     //     println!("Customer {} has entered, taking a bun from the shelf", c);
//     //     let bun = shelf_rcv.recv().unwrap();
//     //     thread::sleep(Duration::from_millis(500));
//     //     println!("Customer {} has taken bun {} and is leaving", c, bun);
//     //     thread::sleep(Duration::from_millis(500));
//     //     c += 1;
//     // }

//     // Multiple Consumer (Loop Producing Multiple Customer Threads)
//     let mut c = 1;
//     loop {
//         let shelf_rcv_clone = shelf_rcv.clone();
//         std::thread::spawn(move || {
//             println!("Customer {} has entered, taking a bun from the shelf", c);
//             let bun = shelf_rcv_clone.recv().unwrap();
//             thread::sleep(Duration::from_millis(500));
//             println!("Customer {} has taken bun {} and is leaving", c, bun);
//             thread::sleep(Duration::from_millis(500));
//         });
//         c += 1;
//         thread::sleep(Duration::from_secs(1));
//     }
// }

// Homework
// extern crate crossbeam_channel;
// use crossbeam_channel::bounded;
extern crate rand;
use rand::Rng;
use std::format;
use std::sync::mpsc;
use std::thread;
use std::time::Duration;

fn main() {
    // vector to house the various stock
    let vec = vec!["aap", "msf", "ibm", "apu", "del"];

    // channel to facilitate communication between stock and listener
    let (sd, rv) = mpsc::channel();

    // Iterate through the vector allocating a thread for each stock
    for v in vec {
        let sd_clone = sd.clone();
        let mut val = 0.01; // Init value for all stocks
        std::thread::spawn(move || {
            let mut rng = rand::thread_rng();
            loop {
                let mut sleep_time = rng.gen_range(2..25);
                let inc = rng.gen_range(0.1..0.8);
                val += inc;
                sleep_time *= 100;
                let stock = format!("{} : {:.2}", v, val);
                sd_clone.send(stock).unwrap();
                thread::sleep(Duration::from_millis(sleep_time));
            }
        });
    }

    loop {
        let stock = rv.recv().unwrap();
        println!("Stock update received: {}", stock);
    }
}
