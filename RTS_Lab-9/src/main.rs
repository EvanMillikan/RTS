use std::sync::{Arc, Mutex};
use std::thread;

/*fn main() {
    let value = Arc::new(Mutex::new(0.05));
    let mut handles = vec![];

    for i in 1..11 {
        let value = Arc::clone(&value);
        let handle = thread::spawn(move || {
            let mut str = value.lock().unwrap();
            *str += 0.01;
            println!("\t Thread: {}: Incremented to: {}", i, str);
        });
        handles.push(handle);
    }

    for handle in handles {
        handle.join().unwrap();
    }

    println!("End result: {}", value.lock().unwrap());
}*/

extern crate rand;
extern crate threadpool;
use rand::Rng;
use std::collections::HashMap;
use std::format;
use std::sync::mpsc;
use std::time::Duration;
use threadpool::ThreadPool;

fn main() {
    // Threadpool
    let stock_pool = ThreadPool::new(5);

    // Declaration of hashmap and vector
    let mut map = HashMap::new();
    let vec = vec!["aap", "msf", "ibm", "apu", "del"];
    let vec_clone = vec.clone();

    // Create a hashmap of key=stock_name value initialize to 0.01
    for v in vec_clone {
        map.insert(v, 0.01);
    }
    let mutex_map = Arc::new(Mutex::new(map));

    //stock pool
    for i in 1..6 {
        let mutex_map_clone = Arc::clone(&mutex_map);
        let vec_clone = vec.clone();
        stock_pool.execute(move || {
            let mut rng = rand::thread_rng();
            loop {
                let mut stock_value = mutex_map_clone.lock().unwrap();
                let random_stock = rng.gen_range(0..5);
                let inc = rng.gen_range(0.1..0.8);
                *stock_value.entry(vec_clone[random_stock]).or_insert(inc) += inc;
                println!(
                    "\t Thread: {}: Incremented {} to: {}",
                    i, vec_clone[random_stock], stock_value[vec_clone[random_stock]]
                );
                thread::sleep(Duration::from_millis(500));
            }
        });
    }

    loop {}
}
