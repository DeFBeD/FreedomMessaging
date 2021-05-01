package com.example.freedommessaging.Manager;

import android.content.Context;
import android.widget.Adapter;

import com.example.freedommessaging.Adapter.BlockAdapter;
import com.example.freedommessaging.Models.BlockModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

        adapter = new BlockAdapter(blocks,context);
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

    //validation of first block
private boolean isFirstBlockValid(){
        BlockModel firstBlock = blocks.get(0);
        if(firstBlock.getIndex()!=0){
            return false;
        }
        if(firstBlock.getPreviousHash() != null){
            return false;
        }

        return  firstBlock.getHash() != null &&
                BlockModel.calculateHash(firstBlock).equals(firstBlock.getHash());
}

    //when new message new block will be created...this will check if valid or not
private boolean isValidNewBlock(@Nullable BlockModel newBlock, @Nullable BlockModel previousBlock){
        if(newBlock != null && previousBlock != null){
            if (previousBlock.getIndex()+1!= newBlock.getIndex()){
                return false;
            }

            if (newBlock.getPreviousHash()== null || !newBlock.getPreviousHash().equals(newBlock.getData())){
                return false;
            }
            return newBlock.getHash()!= null &&
                    BlockModel.calculate_detail(newBlock).equals(newBlock.getHash());

        }
        return false;
}

    //validate
public boolean isBlockChainValid(){
        if(!isFirstBlockValid()){
            return false;
        }
        for (int i =1; i<blocks.size();i++){
            BlockModel currentBlock = blocks.get(i);
            BlockModel previousBlock = blocks.get(i-1);
            if (!isValidNewBlock(currentBlock,previousBlock))
                return false;
        }
        return true;

}

}
