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
extern crate crossbeam_channel;
extern crate threadpool;
use crossbeam_channel::bounded;
use std::thread;
use std::time::Duration;
use threadpool::ThreadPool;

// Modify Bakery Code to use threadpool
fn main() {
    // Channels
    let (rack_send, rack_rcv) = bounded(12);

    let (shelf_send, shelf_rcv) = bounded(10);

    let pool = ThreadPool::new(3);

    //Baker Thread 1
    pool.execute(move || loop {
        for _x in 0..10 {
            let buns = 1;
            rack_send.send(buns).unwrap();
            println!("Baker 1 is baking bread");
            thread::sleep(Duration::from_millis(200));
        }
        println!("Baker 1 is now taking a rest for 7 seconds");
        thread::sleep(Duration::from_secs(7));
    });

    // Worker Thread
    pool.execute(move || loop {
        let buns = rack_rcv.recv().unwrap();
        shelf_send.send(buns).unwrap();
        println!("Worker is moving bread");
        thread::sleep(Duration::from_secs(1));
    });

    // Multiple Consumer (Loop Producing Multiple Customer Threads)
    let mut c = 1;
    loop {
        let shelf_rcv_clone = shelf_rcv.clone();
        pool.execute(move || {
            println!("Customer {} has entered, taking a bun from the shelf", c);
            let bun = shelf_rcv_clone.recv().unwrap();
            thread::sleep(Duration::from_millis(500));
            println!("Customer {} has taken bun {} and is leaving", c, bun);
            thread::sleep(Duration::from_millis(500));
        });
        c += 1;
        thread::sleep(Duration::from_secs(1));
    }
}
