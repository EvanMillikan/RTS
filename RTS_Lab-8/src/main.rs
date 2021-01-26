// Week 8: Learning How to Use Scheduled Threadpool

// Creating bakery project using threadpool
// extern crate crossbeam_channel;
// extern crate threadpool;
// use crossbeam_channel::bounded;
// use std::thread;
// use std::time::Duration;
// use threadpool::ThreadPool;

// // Modify Bakery Code to use threadpool
// fn main() {
//     // Channels
//     let (rack_send, rack_rcv) = bounded(12);
//     let (shelf_send, shelf_rcv) = bounded(10);

//     // Threadpools
//     let bakery_pool = ThreadPool::new(2);
//     let customer_pool = ThreadPool::new(5);

//     //Baker Thread
//     bakery_pool.execute(move || loop {
//         for _x in 0..10 {
//             let buns = 1;
//             rack_send.send(buns).unwrap();
//             println!("Baker 1 is baking bread");
//             thread::sleep(Duration::from_millis(200));
//         }
//         println!("Baker 1 is now taking a rest for 7 seconds");
//         thread::sleep(Duration::from_secs(7));
//     });

//     // Worker Thread
//     bakery_pool.execute(move || loop {
//         let buns = rack_rcv.recv().unwrap();
//         shelf_send.send(buns).unwrap();
//         println!("Worker is moving bread");
//         thread::sleep(Duration::from_secs(1));
//     });

//     // Multiple Consumer (Loop Producing Multiple Customer Threads)
//     let mut c = 1;
//     loop {
//         let shelf_rcv_clone = shelf_rcv.clone();
//         customer_pool.execute(move || {
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

// Creating bakery project using scheduled threadpool
extern crate crossbeam_channel;
extern crate scheduled_thread_pool;
use crossbeam_channel::bounded;
use scheduled_thread_pool::ScheduledThreadPool;
use std::thread;
use std::time::Duration;

fn main() {
    // Channels
    let (rack_send, rack_rcv) = bounded(12);
    let (shelf_send, shelf_rcv) = bounded(10);

    // Threadpools
    let bakery_pool = ScheduledThreadPool::new(2);
    let customer_pool = ScheduledThreadPool::new(2);
    // Bakery Logic
    bakery_pool.execute_at_fixed_rate(Duration::from_secs(0), Duration::from_secs(7), move || {
        for _x in 0..10 {
            let buns = 1;
            rack_send.send(buns).unwrap();
            println!("Baker is baking bread");
            thread::sleep(Duration::from_millis(200));
        }
        println!("Baker is now taking a rest for 7 secs");
    });

    // Worker Logic
    bakery_pool.execute_at_fixed_rate(Duration::from_secs(0), Duration::from_secs(1), move || {
        let buns = rack_rcv.recv().unwrap();
        shelf_send.send(buns).unwrap();
        println!("Worker is moving bread");
    });

    // Customers Logic
    let mut c = 1;
    loop {
        let shelf_rcv_clone = shelf_rcv.clone();
        customer_pool.execute(move || {
            println!("Customer {} has entered, taking a bun from the shelf", c);
            let bun = shelf_rcv_clone.recv().unwrap();
            thread::sleep(Duration::from_millis(500));
            println!("Customer {} has taken bun {} and is leaving", c, bun);
        });
        c += 1;
    }
}
