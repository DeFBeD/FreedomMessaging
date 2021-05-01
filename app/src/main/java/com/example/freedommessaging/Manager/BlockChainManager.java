package com.example.freedommessaging.Manager;

import android.content.Context;
import android.widget.Adapter;

import com.example.freedommessaging.Adapter.BlockAdapter;
import com.example.freedommessaging.Models.BlockModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class BlockChainManager {
    private int difficulty;
    private List<BlockModel> blocks;
    public final BlockAdapter adapter;

    public BlockChainManager(int difficulty, @NonNull Context context) {
        this.difficulty = difficulty;
        blocks = new ArrayList<>();
        BlockModel block =
                new BlockModel(0,System.currentTimeMillis(),
                        null, "Genesis Block");
        block.mineBlock(difficulty);
        blocks.add(block);

        adapter = new BlockAdapter(context,block);
    }

    //new block everytime we send a message, keeps track of blocks.
    private BlockModel latestBlock(String data){

        BlockModel latestBlock = latestBlock();
        return  new BlockModel(
                latestBlock.getIndex()+1,System.currentTimeMillis(),
                latestBlock.getHash(),data);
    }

    private BlockModel latestBlock(){
        return blocks.get(blocks.size()-1);

    }

    public void addBlock(BlockModel block){
        if(block != null){
          block.mineBlock(difficulty);
          blocks.add(block);
        }
    }

private boolean isFirstBlockValid(){
        BlockModel firstBlock =
}


}
